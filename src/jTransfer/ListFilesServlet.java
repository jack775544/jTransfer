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
import java.io.IOException;

/**
 * @author jack775544
 */
@WebServlet(name = "ListFilesServlet")
public class ListFilesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection;

        response.setContentType("text/json");

        if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection){
            connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
            try {
                String resp = "{\"items\": [";
                /*
                 * Format is [
                 *  Filename
                 *  Access Time
                 *  Last Modified Time
                 *  Size (bytes)
                 *  File Type
                 */
                for (ChannelSftp.LsEntry entry : connection.ls()){
                    String item = "{\"filename\": [";
                    item += "\"" + entry.getFilename() + "\", ";
                    item += "\"" + entry.getAttrs().getATime() + "\", ";
                    item += "\"" + entry.getAttrs().getMTime() + "\", ";
                    item += "\"" + entry.getAttrs().getSize() + "\", ";
                    String type;
                    if (entry.getAttrs().isDir()){
                        type = "2";
                    } else if (entry.getAttrs().isLink()){
                        type = "3";
                    } else {
                        type = "1";
                    }
                    item += "\"" + type + "\"]}, ";
                    resp += item;
                }
                resp = resp.substring(0, resp.length() - 2);
                resp += "]}";
                response.getWriter().print(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else {
            response.getWriter().print("Connection is not valid");
        }
    }
}
