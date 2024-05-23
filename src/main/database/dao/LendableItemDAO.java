package database.dao;

import database.ReadingService;
import database.WritingService;
import items.Book;
import items.LendableItem;
import management.ActivityLog;

import java.sql.ResultSet;

public abstract class LendableItemDAO {
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

    public static void create(LendableItem i)
    {
        String sql = "insert into lendable_items values(" + String.valueOf(i.getId()) + ", " +
                toVarchar(i.getName()) + ", " + toVarchar(i.getGenre()) + ", " +
                String.valueOf(i.getTotal()) + ", " + String.valueOf(i.getAvailable()) + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create Lendable Item");
    }

    public static LendableItem read(int id)
    {
        //we'll capture the abstract class' atributtes here
        Book b = new Book();
        String sql = "select * from lendable_items where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            b.setName(rs.getString(2));
            b.setGenre(rs.getString(3));
            b.setTotal(rs.getInt(4));
            b.setAvailable(rs.getInt(5));
            ActivityLog.writeAction("Read Lendable Item");
            return b;
        }
        catch(Exception e)
        {
            System.out.println("Item not found in the db!");
            System.out.println(e);
            return null;
        }
    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "lendable_items");
        ActivityLog.writeAction("Update Lendable Item");
    }

    public static void delete(int id)
    {
        writer.delete(id, "lendable_items");
        ActivityLog.writeAction("Delete Lendable Item");
    }

    public static int getMax() { return reader.getMax("lendable_items"); }
}
