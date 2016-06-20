package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author JASON
 */
@WebServlet(name = "AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(AdminValidationServlet.ADMIN_USER) instanceof AdminUser){
            AdminUser user = (AdminUser) session.getAttribute(AdminValidationServlet.ADMIN_USER);
            if (user.isAuthenticated()){
                response.sendRedirect("./admin");
                return;
            }
        }
        request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
    }
}
