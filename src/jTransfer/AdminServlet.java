package jTransfer;

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

        AdminUser user;
        if (session.getAttribute(AdminValidationServlet.ADMIN_USER) instanceof AdminUser){
            user = (AdminUser) session.getAttribute(AdminValidationServlet.ADMIN_USER);
        } else {
            response.sendRedirect("./adminLogin");
            return;
        }
        if (!user.isAuthenticated()) {
            response.sendRedirect("./adminLogin");
            return;
        }
        AdminSession as = new AdminSession();
        List<Types> result = as.getTypes();
        request.setAttribute("sessions", result);
        request.getRequestDispatcher("adminView.jsp").forward(request, response);
    }
}
