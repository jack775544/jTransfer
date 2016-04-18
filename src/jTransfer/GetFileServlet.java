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

/**
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
			try {
				String file = connection.get(request.getParameter("filename"));
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "filename=" + request.getParameter("filename"));

				response.getWriter().print(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Connection is not valid");
		}
	}
}
