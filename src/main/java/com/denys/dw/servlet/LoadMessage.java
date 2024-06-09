package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.denys.dw.chat.message.Message;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.MessageDAO;

@WebServlet("/loadmessage")
public class LoadMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID = "chat_id_db",
			                    SENDER_ID = "sender_id";
	
	private AbstractDAO<Message> messageDAO = new MessageDAO();
	
    public LoadMessage() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	execute(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

    private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Long chat_id = Long.parseLong(request.getParameter(CHAT_ID));
    	Long sender_id = Long.parseLong(request.getParameter(SENDER_ID));
    	
    	List<Message> list = messageDAO.getAllEntitiesById(chat_id);
    	
    	String data = "";
    	
    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    	
    	DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.ENGLISH);
    	 	
    	for(Message message : list) {
    		
    		Timestamp timestamp = message.getMessage_time();
			String timeToString = timeFormatter.format(timestamp.toLocalDateTime());
			String days = dayFormatter.format(timestamp.toLocalDateTime());
			
			if(!data.contains(days) ) {
				data = data + "<h2>" + days + "</h2>";
			}
			
    		if(message.getSender_id() == sender_id) {
    			
    			if(!message.getMessage().isBlank()) {
    				data = data + "<div class='my-messages'><h3>"+message.getMessage()+"</h3><span>"+timeToString+"</span></div>";
    			} else {
   				     Blob blob = message.getMessage_file();
						if (blob != null) {
							try {
								byte[] array = blob.getBytes(1, (int) blob.length());
								File file = File.createTempFile("smth", ".temp", new File("D:\\Java_Advanced\\DiplomaWork\\src\\main\\webapp\\files\\temporary"));
								FileOutputStream out = new FileOutputStream(file);
								out.write(array);
								out.close();

								if (!isPngFile(file)) {
									byte[] blobFile = blob.getBytes(1, (int)blob.length());
									HttpSession session = request.getSession();
									String fileId = UUID.randomUUID().toString();
									session.setAttribute(fileId, blobFile);
									data = data + "<div class='my-messages'><a href='resultFile.jsp?fileId=" + fileId + "'><img class='file-sign' src='files/sign/file.png'></a><span>" + timeToString + "</span></div>";
								} else {
									String photoBase64 = null;
									try (InputStream inputStream = blob.getBinaryStream()) {
								 		byte[] bytes = new byte[inputStream.available()];
								 		inputStream.read(bytes);
								 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
								 	} catch (Exception e) {
								 		e.printStackTrace();
								 	}
									data = data + "<div class='my-messages'><img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'><span>" + timeToString + "</span></div>";
								}							
	   			                file.delete();
							} catch (SQLException e) {
								e.printStackTrace();
							}
   				     }	 
    			}
    		} else {
    		
    			if(!message.getMessage().isBlank()) {
    				data = data + "<div class='other-user-messages'><h3>"+message.getMessage()+"</h3><span>"+timeToString+"</span></div>";
    			} else {
   				     Blob blob = message.getMessage_file();
						if (blob != null) {
							try {
								byte[] array = blob.getBytes(1, (int) blob.length());
								File file = File.createTempFile("smth", ".temp", new File("D:\\Java_Advanced\\DiplomaWork\\src\\main\\webapp\\files\\temporary"));
								FileOutputStream out = new FileOutputStream(file);
								out.write(array);
								out.close();

								if (!isPngFile(file)) {
									String path = file.getCanonicalPath();
									data = data + "<div class='other-user-messages'><a href='" + path + "'><img class='file-sign' src='files/sign/file.png'></a><span>" + timeToString + "</span></div>";
								} else {
									String photoBase64 = null;
									try (InputStream inputStream = blob.getBinaryStream()) {
								 		byte[] bytes = new byte[inputStream.available()];
								 		inputStream.read(bytes);
								 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
								 	} catch (Exception e) {
								 		e.printStackTrace();
								 	}
									data = data + "<div class='other-user-messages'><img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'><span>" + timeToString + "</span></div>";
								}							
	   			                file.delete();
							} catch (SQLException e) {
								e.printStackTrace();
							}
   				     }	 
    			}
    		}
    	}
    	
    	PrintWriter out = response.getWriter();
    	out.println(data);
	}
    
    private boolean isPngFile(File file) throws IOException {
        byte[] pngSignature = new byte[] {(byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        byte[] fileSignature = new byte[8];

        try (FileInputStream fis = new FileInputStream(file)) {
            if (fis.read(fileSignature) != 8) {
                return false;
            }
        }

        return Arrays.equals(pngSignature, fileSignature);
    }

}
