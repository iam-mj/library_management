package database.dao;

import database.ReadingService;
import database.WritingService;
import items.AudioBook;
import items.Book;
import items.Format;
import management.ActivityLog;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class AudioBookDAO {
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

    public static void create(AudioBook a)
    {
        BookDAO.create(a); // we create the associated book as well
        String sql = "insert into audiobooks values(" + String.valueOf(a.getId()) + ", "
                + String.valueOf(a.getDuration()) + ", " + toVarchar(a.getFormat().toString()) + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create AudioBook");
    }

    public static AudioBook read(int id)
    {
        Book b = BookDAO.read(id); // get the other attributes
        String sql = "select * from audiobooks where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            // set all the new attributes we read
            int duration = rs.getInt(2);
            String format = rs.getString(3);
            // set the format to an enum
            Format actualFormat = Format.stringToFormat(format);
            AudioBook a = new AudioBook(id, b.getName(), b.getGenre(), b.getTotal(), b.getAvailable(),
                                        b.getAuthor(), actualFormat, duration);
            ActivityLog.writeAction("Read AudioBook");
            return a;
        }
        catch(Exception e)
        {
            System.out.println("AudioBook not found in the db!");
            System.out.println(e);
            return null;
        }
    }

    public static List<AudioBook> readAll()
    {
        String sql = "select books.id, name, genre, total, available, id_author, format, duration " +
                "from audiobooks, books, lendable_items where books.id = lendable_items.id and" +
                " audiobooks.id = books.id";
        List<AudioBook> audioBooks = new ArrayList<>();
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
                String format = rs.getString(7);
                int duration = rs.getInt(8);
                Format actualFormat = Format.stringToFormat(format);
                audioBooks.add(new AudioBook(id, name, genre, total, available, AuthorDAO.read(idAuthor),
                                             actualFormat, duration));
            }
            return audioBooks;
        }
        catch(Exception e)
        {
            //System.out.println("There are no AudioBooks");
            return audioBooks;
        }

    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "audiobooks");
        ActivityLog.writeAction("Update AudioBook");
    }

    public static void delete(int id)
    {
        writer.delete(id, "audiobooks");
        ActivityLog.writeAction("Delete AudioBook");
    }
}
