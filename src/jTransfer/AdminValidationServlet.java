package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JasonHughes on 29/05/2016.
 */
public class AdminValidationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if(user.equals("admin") && pass.equals("password")) {
            request.getRequestDispatcher("/adminView.jsp");
            return;
        }

        request.getRequestDispatcher("/adminLogin.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/adminLogin.jsp");
    }
}
