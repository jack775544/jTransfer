package jTransfer;

import com.jcraft.jsch.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author jack775544
 */
public class TransferServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
                e.printStackTrace();
                return;
            }
            session.setAttribute(Connection.CONNECTION_NAME, connection);
        }
        request.getRequestDispatcher("/files.jsp").forward(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        if (!(session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection)){
            response.sendRedirect("/");
        } else {
            doPost(request, response);
        }
    }
}
