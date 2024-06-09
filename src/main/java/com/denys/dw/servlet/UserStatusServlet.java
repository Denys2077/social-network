package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;


@WebServlet("/userStatus")
public class UserStatusServlet extends HttpServlet {
	
	private static final String USER_ID = "user_id";
	private static final String SESSION_ID = "sessionData";
	
	private AbstractDAO<User> userDAO = new UserDAO();
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute(SESSION_ID);
		
		Long user_id = Long.parseLong(request.getParameter(USER_ID));
		User user = userDAO.findEntityById(user_id);
		
		String data = "";
		String status;
         if(sessionData.containsValue(user)) {
        	 status = "Online";
        	 data = data + " <span id='online'>" + status + "</span>";
         } else {
        	 status = "Offline";
        	 data = data + " <span id='offline'>" + status + "</span>";
         }
         
 		PrintWriter out = response.getWriter();
		
 		out.println(data);
         
    }
}
