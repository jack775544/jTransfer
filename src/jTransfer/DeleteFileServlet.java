package jTransfer;

import com.jcraft.jsch.ChannelSftp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by jack775544 on 25/5/2016.
 */
@WebServlet(name = "DeleteFileServlet")
public class DeleteFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection;

        if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection) {
            connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);

            // Set headers
            String path = request.getParameter("filename");
            path = URLDecoder.decode(path, "UTF-8");

            try {
                // Get the SFTP Channel
                ChannelSftp sftpChannel = connection.getSftpChannel();

                sftpChannel.rm(path);
                MySqlLogger.logGeneral("rm triggered on: " + path, session.getId());
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
