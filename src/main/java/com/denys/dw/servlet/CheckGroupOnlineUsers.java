package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

@WebServlet("/checkgrouponlineusers")
public class CheckGroupOnlineUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID_DB = "chat_id_db";
	private static final String SESSION_ID = "sessionData";
	private static final String USER_ID = "user_id";
	
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	
    public CheckGroupOnlineUsers() {
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

	@SuppressWarnings({ "unused", "unchecked" })
	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute(SESSION_ID);
		
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
		
		int count = 0;
		for(User user : users_in_group) {
			if(sessionData.containsValue(user)) {
				count++;
			}
		}
		
		String data = "";
		if(count == 0) {
			data = data + "<span style='color: #FF5858;'>Online: " + count + "</span>";
		} else {
			data = data + "<span>Online: " + count + "</span>";
		}
		PrintWriter out = response.getWriter();
		out.println(data);
	}
}
