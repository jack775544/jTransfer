package jTransfer;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;

/**
 * @author jack775544
 */
@WebServlet(name = "ExecServlet")
public class ExecServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection;
        HttpSession session = request.getSession();

        if (!(session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection)){
            return;
        }

        connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);

        Session sshSession = connection.getSshSession();
        ChannelExec execChannel;
        try {
            execChannel = (ChannelExec) sshSession.openChannel("exec");
        } catch (JSchException e) {
            e.printStackTrace();
            return;
        }

        String pwd = URLDecoder.decode(request.getParameter("pwd"), "UTF-8");
        if (pwd == null){
            return;
        }

        execChannel.setOutputStream(response.getOutputStream());
        execChannel.setCommand("file -ib " + pwd);
        try {
            synchronized (execChannel) {
                try {
                    execChannel.connect();
                    execChannel.wait();
                } catch (InterruptedException f) {
                    f.printStackTrace();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
