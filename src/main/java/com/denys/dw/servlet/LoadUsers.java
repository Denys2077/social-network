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
import java.util.Set;
import java.util.stream.Collectors;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.ContactDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;
import com.denys.dw.user.addition.ContactInfo;
import com.denys.dw.user.contact.Contact;

@WebServlet("/loadusers")
public class LoadUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static final String USER_ID = "user_id";
	
	private AbstractDAO<Contact> contactDAO = new ContactDAO();
	private AbstractDAO<User> userDAO = new UserDAO();
	
    public LoadUsers() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long user_id = Long.parseLong(request.getParameter(USER_ID));
		
		List<Contact> contacts = contactDAO.getAllEntitiesById(user_id);
		List<User> users = userDAO.getAllEntities();
		
		Set<Long> contactIds = contacts.stream()
				                       .map(Contact::getFriend_id)
	                                   .collect(Collectors.toSet());

	    List<User> usersNotInContacts = users.stream()
	                      .filter(user -> !contactIds.contains(user.getId()) && user.getId() != user_id)
	                      .collect(Collectors.toList());
	    String data = "";
	    
	    int id = 0;
	    
	    for(User user : usersNotInContacts) {
	    	ContactInfo info = user.getContactInfo().get();
	    	
	    	 Blob photoBlob = user.getPhoto();
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
	    	data = data + "<div class='user-info-users' id='" + id + "' data-user-id='" + user.getId() + "'>";
	    	 if (photoBlob != null) {
	    		 data = data + "<img src='data:image/jpeg;base64, " + photoBase64 + "' alt='img'>";
	    	 } else {
	    		 data = data + "<img src='files/unknown_user.png' alt='img'>";
	    	 }
			data = data + "<div class='user-message' id='user-info'>" + "<h3 class='contact-full-name'>"
						+ user.getFirstName() + " " + user.getLastName() + "</h3>" + "<h4>Department: "
						+ info.getDepartment() + "</h4>" + "<h4>Position: " + info.getPosition() + "</h4>"
						+ "<h4>Telephone: " + info.getTelephone() + "</h4>" + "</div></div>";
	    	id++;
	    }
	    
	    PrintWriter out = response.getWriter();
	    
		out.println(data);
		
	}

}
