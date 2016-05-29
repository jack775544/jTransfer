package jTransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by JasonHughes on 26/05/2016.
 */
public class AdminSession {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";

    // Database credentials
    // TODO: Change the username and password for a production environment
    private static final String USER = "jtransfer";
    private static final String PASS = "pass";

    // returns a list of all the things in the logging_session database
    public List<Types> getTypes() {
        ResultSet rs;
        List<Types> list = new ArrayList<Types>();

        try{
            Connection con = getConnection();
            String Query = "SELECT * FROM jtransfer.logging_session";
            // System.out.println(Query);

            rs = con.createStatement().executeQuery(Query);

            while (rs.next()) {
                Types type = new Types();
                type.setId(rs.getString(1));
                type.setTimestamp(rs.getString(2));
                type.setSessionId(rs.getString(3));
                type.setLog(rs.getString(4));
                list.add(type);
            }
            rs.close();
            con.close();

        }catch (SQLException e) {
            MySqlLogger.logGeneral(e.getMessage());
        }
        return list;
    }

    // retrieve and return a connection to the logging_session database
    public static Connection getConnection() {
        // instantiate variables
        java.sql.Connection connection = null;

        try {
            // String driverName = "oracle.jdbc.OracleDriver";
            Class.forName(JDBC_DRIVER);

            // Create a connection to the database
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            connection.setCatalog("jtransfer");

            return connection;


        } catch (ClassNotFoundException e) {
            MySqlLogger.logGeneral(e.getMessage());
        } catch (SQLException e) {
            MySqlLogger.logGeneral(e.getMessage());
        }
        return connection;
    }

}
