package jTransfer;

import com.jcraft.jsch.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Vector;

/**
 * Servlet for handling the get requests for the SFTP server
 * @author jack775544
 */
@WebServlet(name = "ListFilesServlet")
public class GetFileServlet extends HttpServlet {
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
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(request.getParameter("name"), "UTF-8"));

			try {
				// Get the SFTP Channel
				ChannelSftp sftpChannel = connection.getSftpChannel();

				// Get the input and output streams
				InputStream in = sftpChannel.get(path);
				OutputStream out = response.getOutputStream();

				// Copy data from input stream to output stream
				byte[] buffer = new byte[8192];
				for (int length; (length = in.read(buffer)) > 0;) {
					out.write(buffer, 0, length);
				}
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