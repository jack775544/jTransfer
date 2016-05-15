package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
		// If the session is set up then do the request, otherwise return to login
		if (!(session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection)){
			response.sendRedirect("./");
		} else {
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        }
	}
}
