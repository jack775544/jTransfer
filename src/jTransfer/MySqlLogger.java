package jTransfer;

import java.sql.*;
import java.util.Date;

/**
 * An error logger for saving logs in MySQL
 * Note: This logger fails silently, meaning that if there is any error, it will simply do nothing but print the log output to stdout
 * @author jack775544
 */
public class MySqlLogger {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";

    // Database credentials
    // TODO: make these gud
    static final String USER = "";
    static final String PASS = "";


    public static boolean logGeneral(String item){
        java.sql.Connection connection = null;
        PreparedStatement addLog = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            connection.setCatalog("jtransfer");

            String sql = "INSERT INTO logging_general (`id`, `timestamp`, `log`) VALUES (NULL, UNIX_TIMESTAMP(), ?)";
            addLog = connection.prepareStatement(sql);
            addLog.setString(1, item);
            addLog.executeUpdate();

            addLog.close();
            connection.close();
        } catch(Exception e){
            // Print the message to stdout instead of to the database then exit
            System.out.println(new Date().getTime() + ": " + item);
            e.printStackTrace();
            return false;
        } finally {
            //finally block used to close resources
            try {
                if (addLog != null) {
                    addLog.close();
                }
            } catch (SQLException se2) {
                // We can't prevent this error, so just fail as gracefully as possible
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                // We can't prevent this error, so just fail as gracefully as possible
            }
        }
        return true;
    }
}
