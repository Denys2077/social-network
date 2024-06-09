package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.ContactDAO;
import com.denys.dw.user.contact.Contact;

@WebServlet("/checkFriendStatus")
public class CheckFriendStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AbstractDAO<Contact> contactDAO = new ContactDAO();
	
    public CheckFriendStatus() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Long currentUserId = Long.parseLong(request.getParameter("currentUserId"));
		Long friendId = Long.parseLong(request.getParameter("friendId"));
		
		boolean isFriend = false;
		List<Contact> contacts = contactDAO.getAllEntities();
		
		for (Contact contact : contacts) {
			if (contact.getCurrent_user_id() == currentUserId && contact.getFriend_id() == friendId) {
				isFriend = true;
				break;
			}
		}
		
		String responseText = "";
		if(isFriend) {
			responseText = responseText + "<a href='#' id='delete-user' onclick='deleteUserFromContact()'>Delete user from contact</a>";
		} else {
			responseText = responseText + "<a href='#' id='add-user' onclick='addUserToContact()'>Add user to contact</a>";
		}
		
		PrintWriter out = response.getWriter();
		out.println(responseText);
	}

}
