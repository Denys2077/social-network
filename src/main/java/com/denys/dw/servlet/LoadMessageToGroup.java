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
import com.denys.dw.chat.message.MessageGroup;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.MessageGroupDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

@WebServlet("/loadmessagestogroup")
public class LoadMessageToGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID = "chat_id_db",
                                SENDER_ID = "sender_id";
	
	private AbstractDAO<MessageGroup> groupDAO = new MessageGroupDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	
    public LoadMessageToGroup() {
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
	    	
	    	List<MessageGroup> list = groupDAO.getAllEntitiesById(chat_id);
	    	
	    	String data = "";
	    	
	    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    	
	    	DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.ENGLISH);
	    	 	
	    	for(MessageGroup message_group : list) {
	    		
	    		Timestamp timestamp = message_group.getMessage_time();
				String timeToString = timeFormatter.format(timestamp.toLocalDateTime());
				String days = dayFormatter.format(timestamp.toLocalDateTime());
				
				if(!data.contains(days) ) {
					data = data + "<h2>" + days + "</h2>";
				}
				
	    		if(message_group.getSender_id() == sender_id) {
	    			
	    			User user = userDAO.findEntityById(message_group.getSender_id());
	    			if(!message_group.getMessage().isBlank()) {			
	    			    data = data + "<div class='my-messages'>";
	    			    data = data + " <p class='name'>" + user.getFirstName() + " " + user.getLastName() + "</p>";
	    				Blob photo_user = user.getPhoto();
	    			    if(photo_user != null) {
	    			    	String photoBase64 = null;
							try (InputStream inputStream = photo_user.getBinaryStream()) {
						 		byte[] bytes = new byte[inputStream.available()];
						 		inputStream.read(bytes);
						 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
						 	} catch (Exception e) {
						 		e.printStackTrace();
						 	}
							data = data + "<img class='user-avatar' src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
	    			    } else {
	    			    	data = data + "<img class='user-avatar' src='files/unknown_user.png' alt='img'>";
	    			    }
	    			    data = data + "<p>" + message_group.getMessage() + "</p><span class='time-message'>" + timeToString + "</span></div>";
	    			} else {
	   				     Blob blob = message_group.getMessage_file();
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
	
										data = data + "<div class='my-messages'>";
										data = data + " <p class='name'>" + user.getFirstName() + " " + user.getLastName() + "</p>";
					    				Blob photo_user = user.getPhoto();
					    			    if(photo_user != null) {
					    			    	String photoBase64 = null;
											try (InputStream inputStream = photo_user.getBinaryStream()) {
										 		byte[] bytes = new byte[inputStream.available()];
										 		inputStream.read(bytes);
										 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
										 	} catch (Exception e) {
										 		e.printStackTrace();
										 	}
											data = data + "<img class='user-avatar' src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
					    			    } else {
					    			    	data = data + "<img class='user-avatar' src='files/unknown_user.png' alt='img'>";
					    			    }
										data = data + "<a href='resultFile.jsp?fileId=" + fileId + "'><img class='file-sign' src='files/sign/file.png'></a><span class='time-message'>" + timeToString + "</span></div>";
									    
									} else {
										String photoBase64 = null;
										try (InputStream inputStream = blob.getBinaryStream()) {
									 		byte[] bytes = new byte[inputStream.available()];
									 		inputStream.read(bytes);
									 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
									 	} catch (Exception e) {
									 		e.printStackTrace();
									 	}
										data = data + "<div class='my-messages'>";
										data = data + " <p class='name'>" + user.getFirstName() + " " + user.getLastName() + "</p>";
					    				Blob photo_user = user.getPhoto();
					    			    if(photo_user != null) {
					    			    	String photoUserBase64 = null;
											try (InputStream inputStream = photo_user.getBinaryStream()) {
										 		byte[] bytes = new byte[inputStream.available()];
										 		inputStream.read(bytes);
										 		photoUserBase64 = Base64.getEncoder().encodeToString(bytes);
										 	} catch (Exception e) {
										 		e.printStackTrace();
										 	}
											data = data + "<img class='user-avatar' src='data:image/jpeg;base64, " + photoUserBase64 + "' alt='img'>";
					    			    } else {
					    			    	data = data + "<img class='user-avatar' src='files/unknown_user.png' alt='img'>";
					    			    }
					    			    
										data = data + "<img class='img-mes' src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'><span class='time-message'>" + timeToString + "</span></div>";
									}						
		   			                file.delete();
								} catch (SQLException e) {
									e.printStackTrace();
								}
	   				     }	 
	    			}
	    			
	    		} else {
	    			
	    			User user = userDAO.findEntityById(message_group.getSender_id());
	    			if(!message_group.getMessage().isBlank()) {			
	    			    data = data + "<div class='other-messages'>";
	    			    data = data + " <p class='name'>" + user.getFirstName() + " " + user.getLastName() + "</p>";
	    				Blob photo_user = user.getPhoto();
	    			    if(photo_user != null) {
	    			    	String photoBase64 = null;
							try (InputStream inputStream = photo_user.getBinaryStream()) {
						 		byte[] bytes = new byte[inputStream.available()];
						 		inputStream.read(bytes);
						 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
						 	} catch (Exception e) {
						 		e.printStackTrace();
						 	}
							data = data + "<img class='user-avatar' src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
	    			    } else {
	    			    	data = data + "<img class='user-avatar' src='files/unknown_user.png' alt='img'>";
	    			    }
	    			    data = data + "<p>" + message_group.getMessage() + "</p><span class='time-message'>" + timeToString + "</span></div>";
	    			} else {
	   				     Blob blob = message_group.getMessage_file();
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
	
										data = data + "<div class='other-messages'>";
										data = data + " <p class='name'>" + user.getFirstName() + " " + user.getLastName() + "</p>";
					    				Blob photo_user = user.getPhoto();
					    			    if(photo_user != null) {
					    			    	String photoBase64 = null;
											try (InputStream inputStream = photo_user.getBinaryStream()) {
										 		byte[] bytes = new byte[inputStream.available()];
										 		inputStream.read(bytes);
										 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
										 	} catch (Exception e) {
										 		e.printStackTrace();
										 	}
											data = data + "<img class='user-avatar' src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
					    			    } else {
					    			    	data = data + "<img class='user-avatar' src='files/unknown_user.png' alt='img'>";
					    			    }
										data = data + "<a href='resultFile.jsp?fileId=" + fileId + "'><img class='file-sign' src='files/sign/file.png'></a><span class='time-message'>" + timeToString + "</span></div>";
									    
									} else {
										String photoBase64 = null;
										try (InputStream inputStream = blob.getBinaryStream()) {
									 		byte[] bytes = new byte[inputStream.available()];
									 		inputStream.read(bytes);
									 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
									 	} catch (Exception e) {
									 		e.printStackTrace();
									 	}
										data = data + "<div class='other-messages'>";
										data = data + " <p class='name'>" + user.getFirstName() + " " + user.getLastName() + "</p>";
					    				Blob photo_user = user.getPhoto();
					    			    if(photo_user != null) {
					    			    	String photoUserBase64 = null;
											try (InputStream inputStream = photo_user.getBinaryStream()) {
										 		byte[] bytes = new byte[inputStream.available()];
										 		inputStream.read(bytes);
										 		photoUserBase64 = Base64.getEncoder().encodeToString(bytes);
										 	} catch (Exception e) {
										 		e.printStackTrace();
										 	}
											data = data + "<img class='user-avatar' src='data:image/jpeg;base64, " + photoUserBase64 + "' alt='img'>";
					    			    } else {
					    			    	data = data + "<img class='user-avatar' src='files/unknown_user.png' alt='img'>";
					    			    }
					    			    
										data = data + "<img class='img-mes' src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'><span class='time-message'>" + timeToString + "</span></div>";
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
