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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

@WebServlet("/loaduserstogroup")
public class LoadUsersToGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID_DB = "chat_id_db";
	private static final String SESSION_ID = "sessionData";
	private static final String USER_ID = "user_id";
	
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	
    public LoadUsersToGroup() {
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
		Long group_id = Long.parseLong(request.getParameter(CHAT_ID_DB));
		Long user_id = Long.parseLong(request.getParameter(USER_ID));
		
		User current_user = userDAO.findEntityById(user_id);
		
		GroupChat chat = groupDAO.findEntityById(group_id);
		String chat_name = chat.getName_group();
		
		List<User> users_in_group = new ArrayList<>();
		List<GroupChat> list = groupDAO.getAllEntities();
		
		for(GroupChat group : list) {
			if(group.getName_group().equals(chat_name)) {
				 Long connectedUserId = group.getConnected_user_id();
			        if (connectedUserId != null) {
			            User user = userDAO.findEntityById(connectedUserId);
			            users_in_group.add(user);
			        }
			}
		}
		
		HttpSession session = request.getSession();
		
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute(SESSION_ID);
		
		String data = getData(current_user, users_in_group, sessionData);
		
		PrintWriter out = response.getWriter();
		out.println(data);
	}
	
	private String getData(User current_user, List<User> users, Map<String, Object> sessionData) {
		String data = "";

		for(User user : users) {
			data = data + "<div class='user-info'>";
			Blob photo = user.getPhoto();
			 if (photo != null) {
				String photoBase64 = null;
			 	try (InputStream inputStream = photo.getBinaryStream()) {
			 		byte[] bytes = new byte[inputStream.available()];
			 		inputStream.read(bytes);
			 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
			 	} catch (Exception e) {
			 		e.printStackTrace();
			 	}
			 	data = data + "<img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
			 } else {
				data = data + "<img src='files/unknown_user.png' alt='img'>";
			 }
			 data = data + "<h3>" + user.getFirstName() + " " + user.getLastName() + "</h3>";
			 if(sessionData.containsValue(user)) {
				 data = data + "<span id='online'>Online</span>";
			 } else {
				 data = data + "<span id='offline'>Offline</span>";
			 }
			 if(user.getId() != current_user.getId()) {
				data = data + "<span class='option-else' onclick='toggleOptionUser(" + user.getId() + ")'><ion-icon name='ellipsis-vertical-outline'></ion-icon></span>";
			 }
			 
			 data = data + "</div>";
 		}
		
		return data;
	}

}
