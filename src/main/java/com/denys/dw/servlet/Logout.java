package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String WINDOW_ID = "window_id";
	private static final String SESSION_ID = "sessionData";
	
    public Logout() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}


	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute(SESSION_ID);
			String windowId = request.getParameter(WINDOW_ID);
			if (sessionData != null && windowId != null && sessionData.containsKey(windowId)) {
				sessionData.remove(windowId);
				session.setAttribute(SESSION_ID, sessionData);
			}
			session.removeAttribute(WINDOW_ID);
		}
	}

}
