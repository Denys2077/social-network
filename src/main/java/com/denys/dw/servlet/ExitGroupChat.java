package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;

@WebServlet("/exitGroupChat")
public class ExitGroupChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CHAT_ID_DB = "chat_id_db",
			                    USER_ID = "user_id";
	
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	
    public ExitGroupChat() {
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
		GroupChat chat = getChat(request);
		if(groupDAO.deleteEntityByParameter(chat)) {
			System.out.println("я вийшов з чату з id =" + request.getParameter(CHAT_ID_DB));
		} else {
			System.out.println("Помилка при виході з чату з id =" + request.getParameter(CHAT_ID_DB));
		}
		PrintWriter out = response.getWriter();
	}
	
	private GroupChat getChat(HttpServletRequest request) {
		Long group_id = Long.parseLong(request.getParameter(CHAT_ID_DB));
		
		GroupChat found = groupDAO.findEntityById(group_id);
		String name = found.getName_group();
		
		Long connected_user_id = Long.parseLong(request.getParameter(USER_ID));
		
		List<GroupChat> list = groupDAO.getAllEntities();
		
	    GroupChat chat = null;
	    for(GroupChat group : list) {
	    	if(group.getName_group().equals(name) && group.getConnected_user_id() == connected_user_id) {
	    		chat = new GroupChat(group.getId(), group.getCreater_id(), group.getName_group(), group.getGroup_photo(), group.getConnected_user_id());
	    	}
	    }
	    return chat;
	}
}
