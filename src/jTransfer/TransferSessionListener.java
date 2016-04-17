package jTransfer;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author jack775544
 */
public class TransferSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        // Have a 300 second (5 minute) timeout on the HTTP session
        System.out.println("session created");
        httpSessionEvent.getSession().setMaxInactiveInterval(300);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("session killed");
        HttpSession session = httpSessionEvent.getSession();
        Object connectionObject = session.getAttribute(Connection.CONNECTION_NAME);
        Connection connection;
        if (connectionObject != null && connectionObject instanceof Connection){
            connection = (Connection) connectionObject;
        } else {
            return;
        }
        connection.closeConnection();
        session.removeAttribute(Connection.CONNECTION_NAME);
    }
}
