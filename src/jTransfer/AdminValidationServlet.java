package jTransfer;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by JasonHughes on 29/05/2016.
 */
public class AdminValidationServlet extends HttpServlet {
    public static final String AUTH_STRING = "ADMIN";
    private static final String SALT = "thisIsASaltyMessageSaltForSaltingMessagesThatNeedToBeSalted";
    private static final String PASS = "f7d80fe50ffa3bac59072fa10fcf46e37e68e4682cfd38052a68d41232208ad8de3771d5b0a90a5cf5cc7cbb8085192738087c1fd542a1df821a0ced6d8e3449";

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

        if(user.equals("admin") && encryptPassword(pass).equals(PASS)) {
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
}
