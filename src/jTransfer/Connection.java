package jTransfer;

import com.jcraft.jsch.*;
import java.util.Vector;

/**
 * @author jack775544
 */
public class Connection {
    public static final String CONNECTION_NAME = "CONNECTION";

    private Session sshSession;
    private ChannelSftp sftpChannel;

    public Connection(String remote, String username, String password) throws SftpException, JSchException {
        JSch jSch = new JSch();
        int port = 22;

        Session session = jSch.getSession(username, remote, port);

        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        sshSession = session;

        Channel channel;
        channel = sshSession.openChannel("sftp");
        channel.connect();
        if (channel instanceof ChannelSftp){
            sftpChannel = (ChannelSftp) channel;
        } else {
            throw new SftpException(1, "The channel could not be established");
        }
    }

	/**
     * Gets a reference to the SFTP Session that is stored in the connection.
     * Use this method with extreme care as it is easy to cause massive bugs with it.
     *
     * Never change the current directory of the server outside of the connection method.
     *
     * @return a reference to the SSH session that is stored inside of the connection.
     */
    public ChannelSftp getSftpChannel(){
        return sftpChannel;
    }

    public void closeConnection() {
        sftpChannel.disconnect();
        sshSession.disconnect();
    }

    public Vector<ChannelSftp.LsEntry> ls(String path) {
        Vector output;
        try {
            output = sftpChannel.ls(path);
        } catch (Exception e) {
            MySqlLogger.logGeneral(e.getMessage());
            return null;
        }
        return output;
    }

    public String pwd(){
        try {
            return sftpChannel.pwd();
        } catch (SftpException e){
            MySqlLogger.logGeneral(e.getMessage());
            return "/";
        }
    }

    public String getFolderPath(String path){
        try {
            sftpChannel.cd(path);
            return sftpChannel.pwd();
        } catch (SftpException e) {
            MySqlLogger.logGeneral(e.getMessage());
            return "/";
        }

    }
}
