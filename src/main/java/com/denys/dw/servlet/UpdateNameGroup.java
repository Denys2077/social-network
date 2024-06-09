package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;

@WebServlet("/updateNameGroup")
public class UpdateNameGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID = "chat_id", 
                                NAME_GROUP = "name_group";
	
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	
    public UpdateNameGroup() {
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

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("CHAT_id = " + request.getParameter(CHAT_ID));
		System.out.println("name group = " + request.getParameter(NAME_GROUP));
		Long chat_id = Long.parseLong(request.getParameter(CHAT_ID));
		GroupChat chat = groupDAO.findEntityById(chat_id);
		
		GroupChat newChat = chat;
		newChat.setName_group(request.getParameter(NAME_GROUP));
		
		if(groupDAO.updateEntityByParameter(newChat)) {
			System.out.println("success update photo");
		} else {
			System.out.println("error update photo");
		}
		PrintWriter out = response.getWriter();
	}

}
