package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.ContactDAO;
import com.denys.dw.user.contact.Contact;

@WebServlet("/deleteUserFromContact")
public class DeleteUserFromContact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String CURRENT_ID = "current_id", 
			                    FRIEND_ID = "friend_id";

	private AbstractDAO<Contact> contactDAO = new ContactDAO();

	public DeleteUserFromContact() {
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
		Long current_user_id = Long.parseLong(request.getParameter(CURRENT_ID));
		Long friend_user_id = Long.parseLong(request.getParameter(FRIEND_ID));

		Contact contact = new Contact(current_user_id, friend_user_id);
		
		if(contact != null) {
			contactDAO.deleteEntityByParameter(contact);
		}
	}
}
