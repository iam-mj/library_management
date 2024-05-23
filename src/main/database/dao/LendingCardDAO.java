package database.dao;

import database.ReadingService;
import database.WritingService;
import items.LendableItem;
import items.LendingCard;
import management.ActivityLog;
import user.Client;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class LendingCardDAO {
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

    public static boolean create(LendingCard lc)
    {
        String sql = "insert into lending_cards values(" + String.valueOf(lc.getId()) + ", "
                + String.valueOf(lc.getClient().getId()) + ", "
                + String.valueOf(lc.getItem().getId())  + ", "
                + lc.getLentDateString() + ", " + lc.getBringBackDateString() + ")";
        boolean success = writer.write(sql);
        ActivityLog.writeAction("Create Lending Card");
        return success;
    }

    public static LendingCard read(int id)
    {
        String sql = "select * from lending_cards where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            // set all the new attributes we read
            Client client = ClientDAO.read(rs.getInt(2)); // get the client
            LendableItem item = LendableItemDAO.read(rs.getInt(3));
            LocalDate lentDate = rs.getDate(4).toLocalDate();
            LocalDate bringBackDate = rs.getDate(5).toLocalDate();
            LendingCard lc = new LendingCard(id, client, item, lentDate, bringBackDate);
            ActivityLog.writeAction("Read Lending Card");
            return lc;
        }
        catch(Exception e)
        {
            System.out.println("Lending card not found in the db!");
            System.out.println(e);
            return null;
        }
    }

    public static SortedSet<LendingCard> readForClient(Client c)
    {
        String sql = "select * from lending_cards where id_client = " +
                     String.valueOf(c.getId());
        SortedSet<LendingCard> lendingCards = new TreeSet<>();
        ResultSet rs = reader.read(sql);
        try
        {
            while(rs.next())
            {
                int id = rs.getInt(1);
                LendableItem item = LendableItemDAO.read(rs.getInt(3));
                LocalDate lentDate = rs.getDate(4).toLocalDate();
                LocalDate bringBackDate = rs.getDate(5).toLocalDate();
                LendingCard lc = new LendingCard(id, c, item, lentDate, bringBackDate);
                lendingCards.add(lc);
            }
            return lendingCards;
        }
        catch(Exception e)
        {
            System.out.println(sql);
            System.out.println("There are no lending cards for the client!");
            return lendingCards;
        }
    }

    /*
    public static List<LendingCard> readAll()
    {
        String sql = "select * from lending_cards";
        List<LendingCard> lendingCards = new ArrayList<>();
        ResultSet rs = reader.read(sql);
        try
        {
            while(rs.next())
            {
                int id = rs.getInt(1);
                Client client = ClientDAO.read(rs.getInt(2)); // get the client
                LendableItem item = LendableItemDAO.read(rs.getInt(3));
                LocalDate lentDate = rs.getDate(4).toLocalDate();
                LocalDate bringBackDate = rs.getDate(5).toLocalDate();
                LendingCard lc = new LendingCard(id, client, item, lentDate, bringBackDate);
                lendingCards.add(lc);
            }
            return lendingCards;
        }
        catch(Exception e)
        {
            //System.out.println("There are no lending cards");
            return lendingCards;
        }

    }*/

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "lending_cards");
        ActivityLog.writeAction("Update Lending Card");
    }

    public static void delete(int id)
    {
        writer.delete(id, "lending_cards");
        ActivityLog.writeAction("Delete Lending Card");
    }

    public static int getMax() { return reader.getMax("lending_cards"); }
}
