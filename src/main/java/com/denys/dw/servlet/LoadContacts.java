package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.ContactDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;
import com.denys.dw.user.addition.ContactInfo;
import com.denys.dw.user.contact.Contact;

@WebServlet("/loadcontacts")
public class LoadContacts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String USER_ID = "user_id";
	
	private AbstractDAO<Contact> contactDAO = new ContactDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	
    public LoadContacts() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long current_user_id = Long.parseLong(request.getParameter(USER_ID));
		List<Contact> contacts = contactDAO.getAllEntitiesById(current_user_id);
		
		String data = "";
		
		int id = 0;
		for(Contact contact : contacts) {
			
			User friend = userDAO.findEntityById(contact.getFriend_id());
			ContactInfo info = friend.getContactInfo().get();
			
			 Blob photoBlob = friend.getPhoto();
			 String photoBase64 = null;
			 if (photoBlob != null) {
			 	try (InputStream inputStream = photoBlob.getBinaryStream()) {
			 		byte[] bytes = new byte[inputStream.available()];
			 		inputStream.read(bytes);
			 		photoBase64 = Base64.getEncoder().encodeToString(bytes);
			 	} catch (Exception e) {
			 		e.printStackTrace();
			 	}
			 }
			 
			data = data + "<div class='user-info-contact' id='" + id + "' data-user-id='" + friend.getId() + "'>";
			 if (photoBlob != null) {
	    		 data = data + "<img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
	    	 } else {
	    		 data = data + "<img src='files/unknown_user.png' alt='img'>";
	    	 }
			data = data 
					+ "<div class='user-message' id='user-info'>"
					+ "<h3 class='fullname'>" + friend.getFirstName() + " " + friend.getLastName() + "</h3>"
					+ "<h4>Department: " + info.getDepartment() + "</h4>"
					+ "<h4>Position: " + info.getPosition() + "</h4>"
					+ "<h4>Telephone: " + info.getTelephone() + "</h4>"
					+ "</div></div>";
			id++;
		}
		
		PrintWriter out = response.getWriter();
		out.println(data);
	}
	

}
