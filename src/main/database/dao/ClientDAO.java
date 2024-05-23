package database.dao;

import database.ReadingService;
import database.WritingService;
import management.ActivityLog;
import user.Client;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class ClientDAO {
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

    public static void create(Client c)
    {
        UserDAO.create(c); // as the associated user
        String sql = "insert into clients values(" + c.getId() + ")";
        writer.write(sql);
        ActivityLog.writeAction("Create Client");
    }

    public static Client read(int id)
    {
        Client c = new Client();
        List<String> list = UserDAO.read(id); // get the other attributes
        String sql = "select * from clients where id = " + String.valueOf(id);
        ResultSet rs = reader.read(sql);
        try{
            rs.next(); // there should only be one entry
            // set all the attributes we read
            c.setId(id);
            c.setFirstName(list.getFirst());
            c.setLastName(list.get(1));
            c.setEmail(list.get(2));
            c.setPassword(list.get(3));
            // we have to find the client's lending cards as well
            c.setLendingCards(LendingCardDAO.readForClient(c));
            ActivityLog.writeAction("Read Client");
            return c;
        }
        catch(Exception e)
        {
            System.out.println("Client not found in the db!");
            System.out.println(sql);
            System.out.println(e);
            return null;
        }
    }

    public static List<Client> readAll()
    {
        String sql = "select clients.id, firstname, lastname, email, password " +
                     "from clients, users, people where clients.id = users.id and " +
                     "clients.id = people.id";
        List<Client> clients = new ArrayList<>();
        ResultSet rs = reader.read(sql);
        try
        {
            while(rs.next())
            {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);
                String password = rs.getString(5);
                Client c = new Client(id, firstName, lastName, email, password);
                c.setLendingCards(LendingCardDAO.readForClient(c));
                clients.add(c);
            }
            return clients;
        }
        catch(Exception e)
        {
            //System.out.println("There are no clients");
            return clients;
        }

    }

    public static void update(int id, String parameter, String value)
    {
        writer.update(id, parameter, value, "clients");
        ActivityLog.writeAction("Update Client");
    }

    public static void delete(int id)
    {
        writer.delete(id, "people"); //should all the way from the people table
        ActivityLog.writeAction("Delete Client");
    }
}
