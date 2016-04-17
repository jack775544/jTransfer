package jTransfer;

import com.jcraft.jsch.*;

import javax.servlet.http.HttpSession;
import java.util.Vector;

/**
 * @author jack775544
 */
public class Connection {
    public static final String CONNECTION_NAME = "CONNECTION";

    private Session sshSession;

    public Connection(String remote, String username, String password) throws SftpException, JSchException {
        sshSession = createSshSession(remote, username, password);
    }

    private Session createSshSession(String remote, String username, String password) throws JSchException, SftpException {
        JSch jSch = new JSch();
        int port = 22;

        Session session = jSch.getSession(username, remote, port);

        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    public void closeConnection() {
        sshSession.disconnect();
    }

    public Vector<ChannelSftp.LsEntry> ls() throws JSchException, SftpException {
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        Vector output = sftpChannel.ls(sftpChannel.pwd());

        sftpChannel.exit();
        return output;
    }
}
