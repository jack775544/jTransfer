package jTransfer;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jack775544
 */
public class AdminUser {
    private String username;
    private String passwordHash;
    private boolean authenticated;
    private static final String SALT = "thisIsASaltyMessageSaltForSaltingMessagesThatNeedToBeSalted";

    /**
     * Creates the object with the username and password, storing the password hash and the username, as well as
     * authenticating the user
     * @param username the username that identifies the user
     * @param password the password of the user
     */
    public AdminUser(String username, String password) {
        this.username = username;
        this.passwordHash = encryptPassword(password);
        authenticated = authenticate(username, this.passwordHash);
    }

    /**
     * Creates the object with the username only and does not perform any authentication
     * @param username the username that identifies the user
     */
    public AdminUser(String username){
        this.username = username;
        this.authenticated = false;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Gets weather the user has previously been authenticated
     * @return true iff the user has been previously authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Authenticates the user if it is not already
     * @param password The password to authenticate using, if the class already has a password set in it and this
     *                 authentication evaluates to true then the old password will be replaced
     * @return true iff the user passes the authentication test
     */
    public boolean authenticateUser(String password) {
        String hash = encryptPassword(password);
        boolean r = authenticate(username, hash);
        if (r){
            this.passwordHash = hash;
            return true;
        }
        return false;
    }

    /**
     * Gets weather this user is in the database. This only checks existence, it does not authenticate the user
     * @return true iff the username appears in the database
     */
    public boolean isUser(){
        String databaseUser = MySqlLogger.USER;
        String databasePassword = MySqlLogger.PASS;
        String databaseUrl = MySqlLogger.DB_URL;
        String databaseClass = MySqlLogger.JDBC_DRIVER;

        java.sql.Connection connection = null;
        PreparedStatement adminQuery = null;

        try {
            Class.forName(databaseClass);
        } catch (ClassNotFoundException e) {
            MySqlLogger.logGeneral("Database class not found");
            return false;
        }

        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            connection.setCatalog("jtransfer");

            String sql = "SELECT count(*) FROM users WHERE username = ?";
            adminQuery = connection.prepareStatement(sql);
            adminQuery.setString(1, username);

            ResultSet resultSet = adminQuery.executeQuery();
            int count = 0;
            // Only have 1 row so only return one value
            resultSet.next();
            count = resultSet.getInt(1);

            // Username's are unique so count can only be 1 or 0
            return count == 1;
        } catch (SQLException e) {
            MySqlLogger.logGeneral(e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (adminQuery != null) {
                    adminQuery.close();
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
        return false;
    }

    /**
     * Authenticates the user's username and password
     * @param username the username
     * @param password the password
     * @return true iff the username and password match one in the database
     */
    private boolean authenticate(String username, String password){
        String databaseUser = MySqlLogger.USER;
        String databasePassword = MySqlLogger.PASS;
        String databaseUrl = MySqlLogger.DB_URL;
        String databaseClass = MySqlLogger.JDBC_DRIVER;

        java.sql.Connection connection = null;
        PreparedStatement adminQuery = null;

        try {
            Class.forName(databaseClass);
        } catch (ClassNotFoundException e) {
            MySqlLogger.logGeneral("Database class not found");
            return false;
        }

        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            connection.setCatalog("jtransfer");

            String sql = "SELECT count(*) FROM users WHERE username = ? AND password = ? AND role = 'ADMIN'";
            adminQuery = connection.prepareStatement(sql);
            adminQuery.setString(1, username);
            adminQuery.setString(2, password);

            ResultSet resultSet = adminQuery.executeQuery();
            int count = 0;
            // Only have 1 row so only return one value
            resultSet.next();
            count = resultSet.getInt(1);

            // Username's are unique so count can only be 1 or 0
            return count == 1;
        } catch (SQLException e) {
            MySqlLogger.logGeneral(e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (adminQuery != null) {
                    adminQuery.close();
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
        return false;
    }

    /**
     * Changes the password of the user iff the user exists. Also changes the classes password hash to match the new
     * password hash in the database
     * @param password the new password to set
     * @return true iff the password has changed
     */
    public boolean updatePassword(String password) {
        String databaseUser = MySqlLogger.USER;
        String databasePassword = MySqlLogger.PASS;
        String databaseUrl = MySqlLogger.DB_URL;
        String databaseClass = MySqlLogger.JDBC_DRIVER;

        java.sql.Connection connection = null;
        PreparedStatement adminQuery = null;
        password = encryptPassword(password);

        try {
            Class.forName(databaseClass);
        } catch (ClassNotFoundException e) {
            MySqlLogger.logGeneral("Database class not found");
            return false;
        }

        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            connection.setCatalog("jtransfer");

            String sql = "UPDATE users SET password = ? WHERE username = ?";
            adminQuery = connection.prepareStatement(sql);
            adminQuery.setString(1, password);
            adminQuery.setString(2, username);

            adminQuery.executeUpdate();

            this.passwordHash = password;
            return true;
        } catch (SQLException e) {
            MySqlLogger.logGeneral(e.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (adminQuery != null) {
                    adminQuery.close();
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
        return false;
    }

    private String encryptPassword(String pass){
        return DigestUtils.sha512Hex(pass.concat(SALT));
    }
}
