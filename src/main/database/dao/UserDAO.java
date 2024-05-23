package database.dao;

import database.ReadingService;
import database.WritingService;
import management.ActivityLog;
import user.User;

import java.sql.ResultSet;
import java.util.List;

public abstract class UserDAO {
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

    public static void create(User u)
    {
        PersonDAO.create(u); // we create the associated person as well
        String sql = "insert into users values(" + u.getId() + ", " +
                toVarchar(u.getEmail()) + ", " + toVarchar(u.getPassword()) + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create User");
    }

    public static List<String> read(int id)
    {
        //we'll capture the abstract class' attributes here
        List<String> list = PersonDAO.read(id);
        String sql = "select * from users where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            ActivityLog.writeAction("Read User");
            return list;
        }
        catch(Exception e)
        {
            System.out.println("User not found in the db!");
            System.out.println(e);
            return list;
        }
    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "users");
        ActivityLog.writeAction("Update User");
    }

    public static void delete(int id)
    {
        writer.delete(id, "people"); //should delete it all the way from the people table
        ActivityLog.writeAction("Delete User");
    }
}
