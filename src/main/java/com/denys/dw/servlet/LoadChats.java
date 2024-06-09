package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.denys.dw.chat.ChatTwoUsers;
import com.denys.dw.chat.GroupChat;
import com.denys.dw.chat.message.Message;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.ChatTwoUsersDAO;
import com.denys.dw.dao.GroupChatDAO;
import com.denys.dw.dao.MessageDAO;
import com.denys.dw.dao.MessageDB;
import com.denys.dw.dao.MessageGroupDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

/*
 * Потрібно дореалізувати
 * */
@WebServlet("/loadchats")
public class LoadChats extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String USER_ID = "user_id";
	private static final String SESSION_ID = "sessionData";
	
	private AbstractDAO<ChatTwoUsers> chatDAO = new ChatTwoUsersDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	private MessageDB messageDB = new MessageDAO();
	
	/**/
	private static AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	private static MessageDB messageDBGroup = new MessageGroupDAO();
	
    public LoadChats() {
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
		Long user_id = Long.parseLong(request.getParameter(USER_ID));
		
		HttpSession session = request.getSession();
		
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute(SESSION_ID);
		
		List<Object> combinedList = new ArrayList<>();
		combinedList.addAll(getGroupChats(user_id));
		combinedList.addAll(getChatTwoUsers(user_id));

		Collections.sort(combinedList, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				if (obj1 instanceof GroupChat && obj2 instanceof GroupChat) {
					return ((GroupChat) obj2).getTime_lastMessage().compareTo(((GroupChat) obj1).getTime_lastMessage());
				} else if (obj1 instanceof ChatTwoUsers && obj2 instanceof ChatTwoUsers) {
					return ((ChatTwoUsers) obj2).getTime_last_message()
							.compareTo(((ChatTwoUsers) obj1).getTime_last_message());
				}
				return 0;
			}
		});
		
		String data = getData(user_id, combinedList, sessionData);
		
		PrintWriter out = response.getWriter();
		
		out.println(data);
	}
	
	private String getData(Long user_id, List<Object> combinedList, Map<String, Object> sessionData) {
		String data = "";
		if(combinedList.size() == 0) {
			data = data + "<h3 class='text-chat-none'>" + "Chats are absent!" + "</h3>";
			data = data + "<a href='#' id='search-user' onclick='search_user_button()'>Find person to chat</a>";
		} else {
			for(int i = 0; i < combinedList.size(); i++) {
				if(combinedList.get(i) instanceof GroupChat) {
					GroupChat chat = (GroupChat)combinedList.get(i);
						Message message = messageDB.getLastMessage(chat.getId());
						Blob photoBlob = chat.getGroup_photo();
						
						 String photoBase64 = null;
						 if (photoBlob != null) {
						 	try (InputStream inputStream = photoBlob.getBinaryStream()) {
						 		byte[] bytes = new byte[inputStream.available()];
						 		inputStream.read(bytes);
						 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
						 	} catch (Exception e) {
						 		e.printStackTrace();
						 	}
						 }
						 
						 DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
						 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	           	         String timestampAsString = timeFormatter.format(chat.getTime_lastMessage().toLocalDateTime());
	           	        
	           	         String dateNow = dateFormatter.format(LocalDateTime.now());
	           	         String messageDate = dateFormatter.format(chat.getTime_lastMessage().toLocalDateTime());
						 
	           	         String timeMessage;
	           	         if(dateNow.equals(messageDate)) {
	           	         	 timeMessage = timestampAsString;
	           	         } else {
	           	             long daysBetween = ChronoUnit.DAYS.between(chat.getTime_lastMessage().toLocalDateTime().toLocalDate(), LocalDateTime.now().toLocalDate());
	           	             timeMessage = daysBetween + " day(s) ago";
	           	         }
	           	         data = data + "<div class='chat-card' card-id=" + i + " group='true' chat-id-db=" + chat.getId() + ">";
	           	         if(chat.getGroup_photo() == null) {
	           	        	 data = data + "<img src='files/unknown_user.png' alt='img'>";
	           	         } else {
	           	        	 data = data + "<img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
	           	         }
	           	         data = data + "<div class='user-message'><h3>" + chat.getName_group() + "</h3>";
	           	        /* if(status.equals("Online")) {
	           	        	data = data + "<span class='status' id='online'>" + status + "</span>";
	                     } else { 
	                    	 data = data + "<span class='status' id='left'>" + status + "</span>";
	                     }  */
	           	         if(message.getSender_id() == user_id) {
	           	        	 if(message.getMessage() != null) {
	           	        		data = data + "<p>" + chat.getLastMessage() + "</p>";
	           	        	 } else {
	           	        		 data = data + "<p></p>";
	           	        	 }
	           	         } else {
	           	        	if(message.getMessage() != null) {
	           	        		data = data + "<p>" + chat.getLastMessage() + "</p>";
	           	        	} else {
	           	        	    data = data + "<p></p>";
	           	        	}
	           	         }
	           	         data = data + "<div class='time-last-message'><span>" + timeMessage + "</span></div></div></div>";
					
				} else if(combinedList.get(i) instanceof ChatTwoUsers) {
					ChatTwoUsers chat = (ChatTwoUsers)combinedList.get(i);
					if(user_id == chat.getFirst_user_id()) {
						 User second_user = userDAO.findEntityById(chat.getSecond_user_id());
						 Message message = messageDB.getLastMessage(chat.getId());
						 
						 Blob photoBlob = second_user.getPhoto();
						 String photoBase64 = null;
						 if (photoBlob != null) {
						 	try (InputStream inputStream = photoBlob.getBinaryStream()) {
						 		byte[] bytes = new byte[inputStream.available()];
						 		inputStream.read(bytes);
						 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
						 	} catch (Exception e) {
						 		e.printStackTrace();
						 	}
						 }

						 DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
						 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	           	         String timestampAsString = timeFormatter.format(chat.getTime_last_message().toLocalDateTime());
	           	        
	           	         String dateNow = dateFormatter.format(LocalDateTime.now());
	           	         String messageDate = dateFormatter.format(chat.getTime_last_message().toLocalDateTime());
	           	        
	           	         String timeMessage;
	           	         if(dateNow.equals(messageDate)) {
	           	         	 timeMessage = timestampAsString;
	           	         } else {
	           	             long daysBetween = ChronoUnit.DAYS.between(chat.getTime_last_message().toLocalDateTime().toLocalDate(), LocalDateTime.now().toLocalDate());
	           	             timeMessage = daysBetween + " day(s) ago";
	           	         }
	           	         String status;
	        	         if(sessionData.containsValue(second_user)) {
	        	        	 status = "Online";
	        	         } else {
	        	        	 status = "Offline";
	        	         }
	        	        
	           	         data = data + "<div class='chat-card' card-id=" + i + " chat-user-id=" + second_user.getId() + " chat-id-db=" + chat.getId() + " group='false'>";
	           	         if(second_user.getPhoto() == null) {
	           	        	 data = data + "<img src='files/unknown_user.png' alt='img'>";
	           	         } else {
	           	        	 data = data + "<img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
	           	         }
	           	         data = data + "<div class='user-message'><h3>" + second_user.getFirstName() + " " + second_user.getLastName() + "</h3>";
	           	         if(status.equals("Online")) {
	           	        	data = data + "<span class='status' id='online'>" + status + "</span>";
	                     } else { 
	                    	 data = data + "<span class='status' id='left'>" + status + "</span>";
	                     }  
	           	         if(message.getSender_id() == user_id) {
	           	        	 if(message.getMessage() != null) {
	           	        		data = data + "<p>Me: " + chat.getLast_message() + "</p>";
	           	        	 } else {
	           	        		 data = data + "<p></p>";
	           	        	 }
	           	         } else {
	           	        	if(message.getMessage() != null) {
	           	        		data = data + "<p>" + chat.getLast_message() + "</p>";
	           	        	} else {
	           	        	    data = data + "<p></p>";
	           	        	}
	           	         }
	           	         data = data + "<div class='time-last-message'><span>" + timeMessage + "</span></div></div></div>";

					  } else if (user_id == chat.getSecond_user_id()) {
						     User first_user = userDAO.findEntityById(chat.getFirst_user_id());
						     Message message = messageDB.getLastMessage(chat.getId());
						     
						     Blob photoBlob = first_user.getPhoto();
							 String photoBase64 = null;
							 if (photoBlob != null) {
							 	try (InputStream inputStream = photoBlob.getBinaryStream()) {
							 		byte[] bytes = new byte[inputStream.available()];
							 		inputStream.read(bytes);
							 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
							 	} catch (Exception e) {
							 		e.printStackTrace();
							 	}
							 }
							 
							 DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
							 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		           	         String timestampAsString = timeFormatter.format(chat.getTime_last_message().toLocalDateTime());
		           	        
		           	         String dateNow = dateFormatter.format(LocalDateTime.now());
		           	         String messageDate = dateFormatter.format(chat.getTime_last_message().toLocalDateTime());
		           	        
		           	         String timeMessage;
		           	         
		           	         if(dateNow.equals(messageDate)) {
		           	         	 timeMessage = timestampAsString;
		           	         } else {
		           	             long daysBetween = ChronoUnit.DAYS.between(chat.getTime_last_message().toLocalDateTime().toLocalDate(), LocalDateTime.now().toLocalDate());
		           	             timeMessage = daysBetween + " day(s) ago";
		           	         }
		           	         
		           	         String status;
		        	         if(sessionData.containsValue(first_user)) {
		        	        	 status = "Online";
		        	         } else {
		        	        	 status = "Offline";
		        	         }
		        	         
		           	         data = data + "<div class='chat-card' card-id=" + i + " chat-user-id=" + first_user.getId() + " chat-id-db=" + chat.getId() + " group='false'>";
		           	         if(first_user.getPhoto() == null) {
		           	        	 data = data + "<img src='files/unknown_user.png' alt='img'>";
		           	         } else {
		           	        	 data = data + "<img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
		           	         }
		           	         data = data + "<div class='user-message'><h3>" + first_user.getFirstName() + " " + first_user.getLastName() + "</h3>";
		           	         if(status.equals("Online")) {
		           	        	data = data + "<span class='status' id='online'>" + status + "</span>";
		                     } else { 
		                    	 data = data + "<span class='status' id='left'>" + status + "</span>";
		                     }  
		           	         if(message.getSender_id() == user_id) {
		           	        	 if(message.getMessage() != null) {
		           	        		data = data + "<p>Me: " + chat.getLast_message() + "</p>";
		           	        	 } else {
		           	        		 data = data + "<p></p>";
		           	        	 }
		           	         } else {
		           	        	if(message.getMessage() != null) {
		           	        		data = data + "<p>" + chat.getLast_message() + "</p>";
		           	        	} else {
		           	        	    data = data + "<p></p>";
		           	        	}
		           	         }
		           	         data = data + "<div class='time-last-message'><span>" + timeMessage + "</span></div></div></div>";
					 }
				}
			}
		}
		return data;
	}

	private List<GroupChat> getGroupChats(long user_id) {
		List<GroupChat> group_list = groupDAO.getAllEntities();
		List<GroupChat> sortedListGroupChat = new ArrayList<>();
		if(group_list.size() != 0) {
			for(int i = 0; i < group_list.size(); i++) {
				 GroupChat chat;
				 if(user_id == group_list.get(i).getCreater_id()) {
					 chat = new GroupChat(group_list.get(i).getId(), 
							              group_list.get(i).getCreater_id(),
							              group_list.get(i).getName_group(),
							              group_list.get(i).getGroup_photo(),
							              group_list.get(i).getConnected_user_id());
					 
					 Message message = messageDBGroup.getLastMessage(group_list.get(i).getId());
					 chat.setLastMessage(message.getMessage());
					 chat.setTime_lastMessage(message.getMessage_time());
					 if(chat.getTime_lastMessage() != null) {
						 sortedListGroupChat.add(chat);
					 }
				 } else if(user_id == group_list.get(i).getConnected_user_id()) {

					 chat = groupDAO.findEntityByTwoParameters(""+group_list.get(i).getCreater_id(),  group_list.get(i).getName_group());
					 chat.setConnected_user_id(user_id);
					 
					 Message message = messageDBGroup.getLastMessage(chat.getId());
					 
					 chat.setLastMessage(message.getMessage());
					 chat.setTime_lastMessage(message.getMessage_time());
					 if(chat.getTime_lastMessage() != null) {
						 sortedListGroupChat.add(chat);
					 }
				}	
			}
		}
		return sortedListGroupChat;
	}
	
	private List<ChatTwoUsers> getChatTwoUsers(long user_id) {
		List<ChatTwoUsers> list = chatDAO.getAllEntities();
		List<ChatTwoUsers> sortedList = new ArrayList<>();
		
		if(list.size() != 0) {
			for(int i = 0; i < list.size(); i++) {
				 ChatTwoUsers chat;
				 if(user_id == list.get(i).getFirst_user_id()) {
					 chat = new ChatTwoUsers(list.get(i).getId(), 
							                              list.get(i).getFirst_user_id(), 
							                              list.get(i).getSecond_user_id());
					 
					 Message message = messageDB.getLastMessage(list.get(i).getId());
					 chat.setLast_message(message.getMessage());
					 chat.setTime_last_message(message.getMessage_time());
					 if(chat.getTime_last_message() != null) {
						 sortedList.add(chat);
					 }
				 } else if(user_id == list.get(i).getSecond_user_id()) {
					 chat = new ChatTwoUsers(list.get(i).getId(), 
                             list.get(i).getFirst_user_id(), 
                             list.get(i).getSecond_user_id());
					 
					 Message message = messageDB.getLastMessage(list.get(i).getId());
					 chat.setLast_message(message.getMessage());
					 chat.setTime_last_message(message.getMessage_time());
					 if(chat.getTime_last_message() != null) {
						 sortedList.add(chat);
					 }
				}	
			}
		}
		return sortedList;
	}
}
