package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jack775544 on 21/4/2016.
 */
@WebServlet(name = "PwdServlet")
public class PwdServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection connection;
		if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection){
			connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
			response.getWriter().print(connection.pwd());
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Connection is not valid");
		}
	}
}
