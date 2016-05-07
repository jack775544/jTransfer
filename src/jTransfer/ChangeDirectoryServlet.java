package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jack775544 on 26/4/2016.
 */
@WebServlet(name = "ChangeDirectoryServlet")
public class ChangeDirectoryServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection connection;

		response.setContentType("text/plain");
		if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection) {
			connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
			try {
				String path = request.getParameter("path");
                boolean success = true;//onnection.cd(path);

				if (success){
					response.getOutputStream().print("good");
				} else {
					response.getOutputStream().print("bad");
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Connection failed");
				e.printStackTrace();
			}
		}
	}
}
