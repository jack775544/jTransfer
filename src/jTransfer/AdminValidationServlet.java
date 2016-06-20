package jTransfer;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by JasonHughes on 29/05/2016.
 */
public class AdminValidationServlet extends HttpServlet {
    public static final String AUTH_STRING = "ADMIN";
    private static final String SALT = "thisIsASaltyMessageSaltForSaltingMessagesThatNeedToBeSalted";
    //private static final String PASS = "f7d80fe50ffa3bac59072fa10fcf46e37e68e4682cfd38052a68d41232208ad8de3771d5b0a90a5cf5cc7cbb8085192738087c1fd542a1df821a0ced6d8e3449";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean adminAuth = false;
        if (session.getAttribute(AUTH_STRING) instanceof Boolean){
            adminAuth = (Boolean) session.getAttribute(AUTH_STRING);
        }

        if (adminAuth){
            response.sendRedirect("./admin");
            return;
        }

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        boolean isAuth = isUser(user, encryptPassword(pass));

        if(isAuth) {
            session.setAttribute(AUTH_STRING, true);
            response.sendRedirect("./admin");
            return;
        }

        session.setAttribute(AUTH_STRING, false);
        response.sendRedirect("./adminLogin");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/adminLogin.jsp");
    }

    private String encryptPassword(String pass){
        return DigestUtils.sha512Hex(pass.concat(SALT));
    }

    private boolean isUser(String username, String password){
        String databaseUser = MySqlLogger.USER;
        String databasePassword = MySqlLogger.PASS;
        String databaseUrl = MySqlLogger.DB_URL;
        String databaseClass = MySqlLogger.JDBC_DRIVER;

        java.sql.Connection connection;
        PreparedStatement adminQuery;

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
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
            // Username's are unique so count can only be 1 or 0
            return count == 1;
        } catch (SQLException e) {
            MySqlLogger.logGeneral(e.getMessage());
        }
        return false;
    }
}
