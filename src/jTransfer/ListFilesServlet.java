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
import java.util.Vector;

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

        if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection){
            connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
            try {
                String resp = "{\"items\": [";
                /*
                 * Format is [filename, last access time, last modified time, size (bytes), file type]
                 */
                String oldPath = request.getParameter("path");
                oldPath = URLDecoder.decode(oldPath, "UTF-8");
                String path = connection.getFolderPath(oldPath);
                MySqlLogger.logGeneral("ls. Path: '" + path + "' Request: '" + oldPath + "'", session.getId());

                Vector<ChannelSftp.LsEntry> entries = connection.ls(path);
                if (entries == null){
                    response.getWriter().print("none");
                    return;
                }
                response.setContentType("text/json");
                for (ChannelSftp.LsEntry entry : entries){
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
                resp += "],\"path\": \"" + path + "\"}";
                response.getWriter().print(resp);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Connection failed");
            }
        }  else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Connection is not valid");
        }
    }
}
