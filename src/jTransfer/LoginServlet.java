package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jack775544 on 15/5/2016.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection;

        // Check if the SSH connection has already been established and create it if not
        if (!(session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection)){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String remote = request.getParameter("remote");
            try {
                connection = new Connection(remote, username, password);
            } catch (Exception e) {
                response.sendRedirect("./logout");
                return;
            }
            session.setAttribute(Connection.CONNECTION_NAME, connection);
        }
        response.sendRedirect("./files");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(404, "There ain't enough room in this servlet for 2 request types");
    }
}
