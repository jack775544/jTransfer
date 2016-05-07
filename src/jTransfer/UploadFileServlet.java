package jTransfer;

import com.jcraft.jsch.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by jack775544 on 29/4/2016.
 */
@WebServlet(name = "UploadFileServlet")
@MultipartConfig
public class UploadFileServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection connection;

		if (session.getAttribute(Connection.CONNECTION_NAME) instanceof Connection){
			connection = (Connection) session.getAttribute(Connection.CONNECTION_NAME);
		} else {
			response.sendRedirect("/index");
			return;
		}

        String pwd = request.getParameter("filename");

		ChannelSftp sftpChannel = connection.getSftpChannel();

		Collection<Part> parts = request.getParts();
		HashMap<String, Part> partMap = getPartMap(parts);
		if (!pwd.endsWith("/")){
			pwd = pwd + "/";
		}
		Part file = partMap.get("file");
		OutputStream out;
		try {
			out = sftpChannel.put(pwd + getFilename(file));
		} catch (SftpException e) {
			MySqlLogger.logGeneral(e.getMessage(), session.getId());
			return;
		}

		InputStream in = file.getInputStream();
		byte[] buffer = new byte[8192];
		for (int length; (length = in.read(buffer)) > 0;) {
			out.write(buffer, 0, length);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	private static String getFilename(Part part){
		String disposition = part.getHeader("Content-Disposition");
		String[] items = disposition.split(";");
		for (String item: items){
			if (item.trim().startsWith("filename")){
				//has the format of: filename="ass1.c"
				StringTokenizer tokenizer = new StringTokenizer(item, "\"");
				tokenizer.nextToken();
				return tokenizer.nextToken();
			}
		}
		return null;
	}

	private static HashMap<String, Part> getPartMap(Collection<Part> parts){
		HashMap<String, Part> partMap = new HashMap<String, Part>();
		for (Part part : parts){
			partMap.put(part.getName(), part);
		}
		return partMap;
	}
}
