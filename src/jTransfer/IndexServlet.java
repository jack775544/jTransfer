package jTransfer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jack775544
 */
@WebServlet(name = "IndexServlet")
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formUrl;
        if (request.getParameter("next") == null){
            formUrl = "login";
        } else {
            formUrl = "login?next=" + request.getParameter("next");
        }
        request.setAttribute("formUrl", formUrl);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
