package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// the class that handles writing to the db
// insert, update, delete
public class WritingService {
    private static Connection con;
    private static WritingService instance = null;

    private WritingService() { con = OracleDB.getConnection(); }

    public static WritingService getInstance() {
        if(instance == null)
            instance = new WritingService();
        return instance;
    }

    public boolean write(String updateSql)
    {
        // process the insertion / update / deletion and return the results
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(updateSql);
        }
        catch(SQLException e)
        {
            System.out.println("Exception when writing to the db!");
            System.out.println(updateSql);
            //System.out.println(e);
            return false;
        }
        return true;
    }

    public void update(int id, String parameter, String value, String table)
    {
        String sql = "update " + table + " set " + parameter + " = " + value +
                " where id = " + String.valueOf(id);
        write(sql);
    }

    public void delete(int id, String table)
    {
        String sql = "delete from " + table + " where id = " + String.valueOf(id);
        write(sql);
    }
}
