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

@WebServlet("/deleteChat")
public class DeleteChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID = "chat_id_db";
	
	private AbstractDAO<ChatTwoUsers> chatDAO = new ChatTwoUsersDAO();
	
    public DeleteChat() {
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
	
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		String chat_id_asString = request.getParameter(CHAT_ID);
		
		Long chat_id = Long.parseLong(chat_id_asString);
		
		ChatTwoUsers chat = chatDAO.findEntityById(chat_id);
		if(chat != null && chatDAO.deleteEntityByParameter(chat)) {
			obj.put("msg", "1");
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
		} else {
			obj.put("msg", "3");
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
		}
		
	}

}
