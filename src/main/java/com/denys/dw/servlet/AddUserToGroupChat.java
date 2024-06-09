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

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;

@WebServlet("/addUserToGroupChat")
public class AddUserToGroupChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private static final String FRIENT_ID = "friend_id_value",
    		                    CHAT_ID_DB = "chat_id_db";
    
    private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
    
    public AddUserToGroupChat() {
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
	
	@SuppressWarnings("unchecked")
	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long chat_id_db = Long.parseLong(request.getParameter(CHAT_ID_DB));
		
		GroupChat chat = groupDAO.findEntityById(chat_id_db);
		
		Long connected_user_id = Long.parseLong(request.getParameter(FRIENT_ID));
		
		GroupChat expected = new GroupChat(chat.getCreater_id(), chat.getName_group(), chat.getGroup_photo(), connected_user_id);
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		PrintWriter out = response.getWriter();
		
		if(groupDAO.findEntityByObject(expected) == null) {
			if(groupDAO.createEntityByParameter(expected)) {
				obj.put("chat_id_db", chat_id_db);
				list.add(obj);
				out.println(list.toJSONString());
				out.flush();
				}
			} else {
				System.out.println("Неможливо добавити існуючого користувача в групу!");
			}
	}

}
