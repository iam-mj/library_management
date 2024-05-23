package database.dao;

import database.ReadingService;
import database.WritingService;
import items.Author;
import management.ActivityLog;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class AuthorDAO {
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

    public static void create(Author a)
    {
        PersonDAO.create(a); // we create the associated person as well
        String sql = "insert into authors values(" + a.getId() + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create Author");
    }

    public static Author read(int id)
    {
        Author a = new Author();
        List<String> list = PersonDAO.read(id); // get the other attributes
        String sql = "select * from authors where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            // set all the attributes we read
            a.setFirstName(list.get(0));
            a.setLastName(list.get(1));
            ActivityLog.writeAction("Read Author");
            return a;
        }
        catch(Exception e)
        {
            System.out.println("Author not found in the db!");
            System.out.println(e);
            return null;
        }
    }

    public static List<Author> readAll()
    {
        String sql = "select authors.id, firstname, lastname " +
                "from authors, people where authors.id = people.id";
        List<Author> authors = new ArrayList<>();
        ResultSet rs = reader.read(sql);
        try
        {
            while(rs.next())
            {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                authors.add(new Author(id, firstName, lastName));
            }
            return authors;
        }
        catch(Exception e)
        {
            //System.out.println("There are no authors");
            return authors;
        }

    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "authors");
        ActivityLog.writeAction("Update Author");
    }

    public static void delete(int id)
    {
        writer.delete(id, "authors");
        ActivityLog.writeAction("Delete Author");
    }
}
