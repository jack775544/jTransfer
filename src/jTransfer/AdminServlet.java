package jTransfer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by JasonHughes on 26/05/2016.
 */
public class AdminServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        boolean auth = false;
        if (session.getAttribute(AdminValidationServlet.AUTH_STRING) instanceof Boolean){
            auth = (Boolean) session.getAttribute(AdminValidationServlet.AUTH_STRING);
        }
        if (!auth) {
            response.sendRedirect("./adminLogin");
            return;
        }
        AdminSession as = new AdminSession();
        List<Types> result = as.getTypes();
        //response.getWriter().print("hello world");
        request.setAttribute("sessions", result);
        request.getRequestDispatcher("adminView.jsp").forward(request, response);
        //RequestDispatcher view = request.getRequestDispatcher("adminView.jsp");
        //view.forward(request, response);
    }
}
