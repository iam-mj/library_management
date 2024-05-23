package database;

import java.sql.*;
public class OracleDB{
    private static final String url =
            "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String user = "maria";
    private static final String password = "oracle";
    private static Connection con;

    private OracleDB() {}

    public static Connection getConnection() {
        if(con == null)
        {
            try{
                // load the driver class
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // create the connection object
                con = DriverManager.getConnection(url, user, password);

            }
            catch(Exception e){
                System.out.println("Exceptie prinsa la deschiderea conexiunii catre db:");
                System.out.println(e);
            }
        }
        return con;
    }

    public static void closeConnection() {
        try
        {
            con.close();
            con = null;
        }
        catch(Exception e)
        {
            System.out.println("Exceptie prinsa la inchiderea conexiunii!");
        }
    }
}
