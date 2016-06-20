package jTransfer;

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
    public static final String ADMIN_USER = "ADMIN_USER";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminUser adminUser;
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Get user if exists, if not then create it
        if (session.getAttribute(ADMIN_USER) instanceof AdminUser){
            adminUser = (AdminUser) session.getAttribute(ADMIN_USER);
        } else {
            adminUser = new AdminUser(user, pass);
        }

        boolean auth = adminUser.isAuthenticated();
        // Continue on if authenticated
        if(auth) {
            session.setAttribute(ADMIN_USER, adminUser);
            response.sendRedirect("./admin");
            return;
        }

        // Not authenticated then leave
        response.sendRedirect("./adminLogin");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/adminLogin.jsp");
    }
}
