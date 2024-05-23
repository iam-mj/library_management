package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// the class with whose instance we read from the db
// select
public class ReadingService {
    private static Connection con;
    private static ReadingService instance = null;

    private ReadingService() { con = OracleDB.getConnection(); }

    public static ReadingService getInstance() {
        if(instance == null)
            instance = new ReadingService();
        return instance;
    }

    public ResultSet read(String selectSql)
    {
        // process the select query and return the results
        try{
            Statement stmt = con.createStatement();
            return stmt.executeQuery(selectSql);
        }
        catch(SQLException e)
        {
            System.out.println("Exception when reading from the db!");
            System.out.println(e);
            return null;
        }

    }

    public int getMax(String table)
    {
        String sql = "select max(id) from " + table;
        ResultSet rs = read(sql);
        try
        {
            rs.next(); // there should only be one row
            int count = rs.getInt(1);
            return count;
        }
        catch(Exception e)
        {
            System.out.println("Exception when searching for the max id in table " + table);
            System.out.println(e);
            return 0;
        }
    }
}
