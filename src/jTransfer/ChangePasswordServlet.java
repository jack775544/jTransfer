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
@WebServlet(name = "ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Get user if exists
        AdminUser user;
        if (session.getAttribute(AdminValidationServlet.ADMIN_USER) instanceof AdminUser) {
            user = (AdminUser) session.getAttribute(AdminValidationServlet.ADMIN_USER);
        } else {
            //response.sendError(500, "User is not logged in");
            response.sendRedirect("./adminLogin");
            return;
        }

        // Leave if user is not authenticated
        if (!user.isAuthenticated()) {
            response.sendRedirect("./adminLogin");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");

        // Check if the new password is the same as the confirmation
        if (!newPassword.equals(newPasswordConfirm)) {
            response.sendRedirect("./change");
            return;
        }

        // Authenticate the old password that is provided
        boolean auth = user.authenticateUser(oldPassword);
        if (!auth) {
            response.sendRedirect("./change");
            return;
        }

        // Doubly checked everything, now go ahead with password change
        user.updatePassword(newPassword);
        response.sendRedirect("./admin");
    }
}
