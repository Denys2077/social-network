package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.chat.message.MessageGroup;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;
import com.denys.dw.dao.MessageGroupDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

@WebServlet("/creatingGroupChat")
public class CreateGroupChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String NAME = "name",
			                    USER_ID = "user_id";
	
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	private AbstractDAO<MessageGroup> messageDAO = new MessageGroupDAO();
	
    public CreateGroupChat() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();

		String msg;
		
		Long user_id = Long.parseLong(request.getParameter(USER_ID));
		User creater = userDAO.findEntityById(user_id);
		String group_name = request.getParameter(NAME).trim();
		
		GroupChat chat = new GroupChat(creater.getId(), group_name, null, creater.getId());
		
		GroupChat expected = groupDAO.findEntityByTwoParameters(request.getParameter(USER_ID), group_name);
		
		if(expected == null) {
			if(groupDAO.createEntityByParameter(chat)) {
				GroupChat result = groupDAO.findEntityByTwoParameters(request.getParameter(USER_ID), group_name);
				MessageGroup message = new MessageGroup(result.getId(), result.getCreater_id(), "Hello! I'm an admin!", null, new Timestamp(System.currentTimeMillis()));
			    if(messageDAO.createEntityByParameter(message)) {
			    	msg = "1";
					obj.put("msg", msg);
					obj.put("chat_id_db", result.getId());
					list.add(obj);
					out.println(list.toJSONString());
					out.flush();
			    }
			}
		} else {
			System.out.println("Неможливо зіставити вже існуючий чат");
		}
	}
}
