package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author jack775544
 */
@WebServlet(name = "PasswordChangeInterfaceServlet")
public class PasswordChangeInterfaceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminUser user;
        if (session.getAttribute(AdminValidationServlet.ADMIN_USER) instanceof AdminUser){
            user = (AdminUser) session.getAttribute(AdminValidationServlet.ADMIN_USER);
        } else {
            response.sendRedirect("./adminLogin");
            return;
        }
        if (user.isAuthenticated()) {
            request.setAttribute("loginName", user.getUsername());
            request.getRequestDispatcher("/adminChange.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("./adminLogin");
    }
}
