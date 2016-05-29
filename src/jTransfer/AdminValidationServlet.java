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
    public static final String AUTH_STRING = "ADMIN";

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

        if(user.equals("admin") && pass.equals("password")) {
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
}
