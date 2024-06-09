package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;

@WebServlet("/deleteGroupChat")
public class DeleteGroupChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CHAT_ID_DB = "chat_id_db";
			
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
    public DeleteGroupChat() {
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
	
	private void execute(HttpServletRequest request, HttpServletResponse response) {
		Long chat_id = Long.parseLong(request.getParameter(CHAT_ID_DB));
		
		GroupChat found_chat = groupDAO.findEntityById(chat_id);
		
		List<GroupChat> listchats = groupDAO.getAllEntities();
		
		List<GroupChat> foundChat = new ArrayList<>();
		for(GroupChat chat : listchats) {
			if(chat.getCreater_id() == found_chat.getCreater_id() && chat.getName_group().equals(found_chat.getName_group())) {
				foundChat.add(chat);
			}
		}
		
		for(GroupChat chat : foundChat) {
			groupDAO.deleteEntityByParameter(chat);
		}
	}

}
