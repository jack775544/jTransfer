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
        httpSessionEvent.getSession().setMaxInactiveInterval(300);
        MySqlLogger.logGeneral("session created", httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        MySqlLogger.logGeneral("session destroyed", session.getId());
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
