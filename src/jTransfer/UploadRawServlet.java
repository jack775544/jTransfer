package jTransfer;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.Arrays;

/**
 * Created by jack775544 on 24/5/2016.
 */
@WebServlet(name = "UploadRawServlet")
public class UploadRawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection;
        if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection){
            connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
        } else {
            MySqlLogger.logGeneral("Item not found", session.getId());
            return;
        }
        String path = URLDecoder.decode(request.getParameter("path"), "UTF-8");
        byte[] file = request.getParameter("contents").getBytes();

        if (path == null){
            MySqlLogger.logGeneral("Bad post data:\n Path: null\nFile: " + Arrays.toString(file), session.getId());
            response.sendError(500, "Malformed post data");
            return;
        }

        ChannelSftp sftpChannel = connection.getSftpChannel();

        OutputStream out;
        try {
            out = sftpChannel.put(path);
        } catch (SftpException e) {
            MySqlLogger.logGeneral(e.getMessage(), session.getId());
            return;
        }

        out.write(file);

        MySqlLogger.logGeneral("put triggered", session.getId());

        try {
            // Need to close and reopen the channel due to a bug in Jsch
            connection.createChannel();
        } catch (SftpException e) {
            MySqlLogger.logGeneral(e.getMessage(), session.getId());
        } catch (JSchException e) {
            MySqlLogger.logGeneral(e.getMessage(), session.getId());
        }

        response.getWriter().print("uploaded");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);

        return b.toByteArray();

    }
}
