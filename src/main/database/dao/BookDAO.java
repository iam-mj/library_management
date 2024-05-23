package database.dao;

import database.ReadingService;
import database.WritingService;
import items.Book;
import management.ActivityLog;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class BookDAO {
    private static ReadingService reader;
    private static WritingService writer;

    static {
        reader = ReadingService.getInstance();
        writer = WritingService.getInstance();
    }

    private static String toVarchar(String s)
    {
        return '\'' + s + '\'';
    }

    public static void create(Book b)
    {
        LendableItemDAO.create(b); // we create the associated item as well
        AuthorDAO.create(b.getAuthor()); // and the associated author
        String sql = "insert into books values(" + String.valueOf(b.getId()) + ", "
                     + String.valueOf(b.getAuthor().getId()) + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create Book");
    }

    public static Book read(int id)
    {
        Book b = (Book) LendableItemDAO.read(id); // get the other attributes
        String sql = "select * from books where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            // set all the new attributes we read
            b.setAuthor(AuthorDAO.read(rs.getInt(2)));
            ActivityLog.writeAction("Read Book");
            return b;
        }
        catch(Exception e)
        {
            System.out.println("Book not found in the db!");
            System.out.println(e);
            return null;
        }
    }

    public static List<Book> readAll()
    {
        String sql = "select books.id, name, genre, total, available, id_author " +
                "from books, lendable_items where books.id = lendable_items.id and " +
                "books.id not in (select id from audiobooks)"; // we don't want to take any audiobooks
        List<Book> books = new ArrayList<>();
        ResultSet rs = reader.read(sql);
        try
        {
            while(rs.next())
            {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String genre = rs.getString(3);
                int total = rs.getInt(4);
                int available = rs.getInt(5);
                int idAuthor = rs.getInt(6);
                books.add(new Book(id, name, genre, total, available, AuthorDAO.read(idAuthor)));
            }
            return books;
        }
        catch(Exception e)
        {
            //System.out.println("There are no books");
            return books;
        }

    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "books");
        ActivityLog.writeAction("Update Book");
    }

    public static void delete(int id)
    {
        writer.delete(id, "books");
        ActivityLog.writeAction("Delete Book");
    }
}
