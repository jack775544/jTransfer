package jTransfer;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

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
 * Edit file servlet
 * Created by jack775544 on 21/4/2016.
 */
@WebServlet(name = "EditFileServlet")
public class EditFileServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection connection;

		if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection) {
			connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);

			// Set headers
			String filename = request.getParameter("filename");
			String path = request.getParameter("pwd");

			path = URLDecoder.decode(path, "UTF-8") + "/" + filename;

			try {
				// Get the ssh Session
				ChannelSftp sftpChannel = connection.getSftpChannel();

				// Get the input and output streams
				InputStream in = sftpChannel.get(path);
				String file = "";
				int character;
				while ((character = in.read()) != -1){
					file += (char) character;
				}
				request.setAttribute("file", file);
				request.getRequestDispatcher("/edit.jsp").forward(request, response);
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
