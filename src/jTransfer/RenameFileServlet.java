package jTransfer;

import com.jcraft.jsch.ChannelSftp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by jack775544 on 25/5/2016.
 */
@WebServlet(name = "RenameFileServlet")
public class RenameFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection;

        if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection) {
            connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);

            // Set headers
            String oldPath = request.getParameter("filename");
            String newPath = request.getParameter("newname");
            oldPath = URLDecoder.decode(oldPath, "UTF-8");
            newPath = URLDecoder.decode(newPath, "UTF-8");

            try {
                // Get the SFTP Channel
                ChannelSftp sftpChannel = connection.getSftpChannel();
                sftpChannel.rename(oldPath, newPath);

                MySqlLogger.logGeneral("Renamed: " + oldPath + " to: " + newPath, session.getId());
            } catch (Exception e) {
                // Should never happen, I hope
                MySqlLogger.logGeneral(e.getMessage(), session.getId());
            }
        } else {
            // Bad connection, throw 500 error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Connection is not valid");
        }
    }
}
