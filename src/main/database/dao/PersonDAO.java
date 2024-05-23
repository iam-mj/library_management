package database.dao;

import database.ReadingService;
import database.WritingService;
import management.ActivityLog;
import user.Person;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class PersonDAO {
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

    public static void create(Person p)
    {
        String sql = "insert into people values(" + p.getId() + ", " +
                        toVarchar(p.getFirstName()) + ", " + toVarchar(p.getLastName()) + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create Person");
    }

    public static List<String> read(int id)
    {
        List<String> list = new ArrayList<>(); //we'll capture the abstract class' attributes here
        String sql = "select * from people where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            ActivityLog.writeAction("Read Person");
            return list;
        }
        catch(Exception e)
        {
            System.out.println("Person not found in the db!");
            System.out.println(e);
            return list;
        }
    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "people");
        ActivityLog.writeAction("Update Person");
    }

    public static void delete(int id)
    {
        writer.delete(id, "people");
        ActivityLog.writeAction("Delete Person");
    }

    public static int getMax() { return reader.getMax("people"); }
}
