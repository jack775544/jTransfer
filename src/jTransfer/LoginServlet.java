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

        // If an existing connection exists
        if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection){
            Connection c = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
            c.closeConnection();
            session.removeAttribute(Connection.CONNECTION_NAME);
        }

        Connection connection;

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

        if (request.getParameter("next") == null) {
            response.sendRedirect("./files");
        } else {
            response.sendRedirect(request.getParameter("next"));
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(404, "There ain't enough room in this servlet for 2 request types");
    }
}
