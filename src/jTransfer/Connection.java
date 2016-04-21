package jTransfer;

import com.jcraft.jsch.*;
import java.util.Vector;

/**
 * @author jack775544
 */
public class Connection {
    public static final String CONNECTION_NAME = "CONNECTION";

    private Session sshSession;
    private String pwd;

    public Connection(String remote, String username, String password) throws SftpException, JSchException {
        sshSession = createSshSession(remote, username, password);
        pwd = pwd();
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

	/**
     * Gets a reference to the SSH Session that is stored in the connection.
     * Use this method with extreme care as it is easy to cause massive bugs with it.
     *
     * Never change the current directory of the server outside of the connection method.
     *
     * @return a reference to the SSH session that is stored inside of the connection.
     */
    public Session getSshSession(){
        return sshSession;
    }

    public void closeConnection() {
        sshSession.disconnect();
    }

    public Vector<ChannelSftp.LsEntry> ls() {
        Channel channel = null;
        try {
            channel = sshSession.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            return null;
        }
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        Vector output = null;
        try {
            output = sftpChannel.ls(sftpChannel.pwd());
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            sftpChannel.exit();
        }

        sftpChannel.exit();
        return output;
    }

    public String pwd() {
        Channel channel = null;
        try {
            channel = sshSession.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            return null;
        }

        ChannelSftp sftpChannel = (ChannelSftp) channel;

        String output = null;
        try {
            output = sftpChannel.pwd();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            sftpChannel.exit();
        }

        return output;
    }

    public boolean cd(String path) {
        Channel channel;
        try {
            channel = sshSession.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        }

        ChannelSftp sftpChannel = (ChannelSftp) channel;

        try {
            sftpChannel.cd(path);
        } catch (SftpException e) {
            return false;
        } finally {
            sftpChannel.exit();
            // Since we changed the file location we need to change pwd
            pwd = pwd();
        }

        sftpChannel.exit();
        return true;
    }
}
