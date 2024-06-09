package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.chat.ChatTwoUsers;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.ChatTwoUsersDAO;

@WebServlet("/createChat")
public class CreateChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String CURRENT_USER_ID = "current_id_value",
			                    CHAT_USER_TO = "friend_id_value",
			                    WINDOW_ID = "windowId";
	                          
	private AbstractDAO<ChatTwoUsers> chatDAO = new ChatTwoUsersDAO();
	
    public CreateChat() {
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
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Long current_user_id = Long.parseLong(request.getParameter(CURRENT_USER_ID));
    	Long chat_user_to = Long.parseLong(request.getParameter(CHAT_USER_TO));
    	String windowId = request.getParameter(WINDOW_ID);
    	
    	ChatTwoUsers chat = new ChatTwoUsers();
    	chat.setFirst_user_id(current_user_id);
    	chat.setSecond_user_id(chat_user_to);
    	
    	ChatTwoUsers expected_one = chatDAO.findEntityByObject(chat);
    	
    	JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
    	
		ChatTwoUsers backward_chat = new ChatTwoUsers();
    	chat.setFirst_user_id(chat_user_to);
    	chat.setSecond_user_id(current_user_id);
    	
    	ChatTwoUsers expected_two = chatDAO.findEntityByObject(chat);
    	if(expected_one != null && expected_two == null) { // чат был найден
    		
    		obj.put("windowId", windowId);
    		obj.put("chat_id_db", expected_one.getId());
    		obj.put("chat_user", expected_one.getSecond_user_id());
    		
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
			
    	} else if(expected_one == null && expected_two != null) {
    		obj.put("windowId", windowId);
    		obj.put("chat_id_db", expected_two.getId());
    		obj.put("chat_user", expected_two.getFirst_user_id());
    		
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
    	} else {
    		if(chatDAO.createEntityByParameter(chat)) {
    			ChatTwoUsers result = chatDAO.findEntityByObject(chat);
    			obj.put("windowId", windowId);
        		obj.put("chat_id_db", result.getId());
        		obj.put("chat_user", result.getSecond_user_id());
        		
    			list.add(obj);
    			out.println(list.toJSONString());
    			out.flush();
    		}
    	}
    }

}
