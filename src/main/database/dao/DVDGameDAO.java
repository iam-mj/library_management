package database.dao;

import database.ReadingService;
import database.WritingService;
import items.Book;
import items.DVDGame;
import items.LendableItem;
import management.ActivityLog;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class DVDGameDAO {
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

    public static void create(DVDGame g)
    {
        LendableItemDAO.create(g); // we create the associated item as well
        String sql = "insert into dvd_games values(" + String.valueOf(g.getId()) + ", "
                + toVarchar(String.valueOf(g.getCompany())) + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create DVD Game");
    }

    public static DVDGame read(int id)
    {
        LendableItem l = LendableItemDAO.read(id); // get the other attributes
        String sql = "select * from dvd_games where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            // set all the new attributes we read
            String company = rs.getString(2);
            ActivityLog.writeAction("Read DVD Game");
            return new DVDGame(id, l.getName(), l.getGenre(), l.getTotal(), l.getAvailable(), company);
        }
        catch(Exception e)
        {
            System.out.println("DVD Game not found in the db!");
            System.out.println(e);
            return null;
        }
    }

    public static List<DVDGame> readAll()
    {
        String sql = "select dvd_games.id, name, genre, total, available, company " +
                "from dvd_games, lendable_items where dvd_games.id = lendable_items.id";
        List<DVDGame> games = new ArrayList<>();
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
                String company = rs.getString(6);
                games.add(new DVDGame(id, name, genre, total, available, company));
            }
            return games;
        }
        catch(Exception e)
        {
            //System.out.println("There are no DVD games");
            return games;
        }

    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "dvd_games");
        ActivityLog.writeAction("Update DVD Game");
    }

    public static void delete(int id)
    {
        writer.delete(id, "dvd_games");
        ActivityLog.writeAction("Delete DVD Game");
    }
}
