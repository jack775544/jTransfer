package jTransfer;

import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author jack775544
 */
public class TransferServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        // If the session is set up then do the request, otherwise return to login
        if (!(session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection)){
            String url = request.getRequestURI();
            String query = request.getQueryString();
            if (query != null){
                url += "?" + query;
            }
            response.sendRedirect("./?next=" + url);
        } else {
            request.getRequestDispatcher("/files.jsp").forward(request, response);
        }
    }
}
