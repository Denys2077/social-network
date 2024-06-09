package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.content.SessionRequestContent;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.logic.LoginLogic;
import com.denys.dw.resource.MessageManager;
import com.denys.dw.user.User;

@WebServlet("/login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String AS_ADMIN = "asAdmin";
	private static final String REMEMBER = "remember";
	
	private AbstractDAO<User> daoUser = new UserDAO();
	
    public Login() {
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

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		SessionRequestContent content = new SessionRequestContent();
		content.extractValues(request);
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		String msg;
		
		String windowId = UUID.randomUUID().toString();
		
		String enterUsername = request.getParameter(USERNAME);
		String enterPass = request.getParameter(PASSWORD);
		String enterAsAdmin = request.getParameter(AS_ADMIN);
		boolean asAdmin = false;
		
		if(enterAsAdmin != null && enterAsAdmin.equals("on")) {
			asAdmin = true;
		}
		
		User user = null;
		
		if(LoginLogic.checkLogin(enterUsername, enterPass)) { /* пошук адміністратора */
			try {
				user = LoginLogic.getUser(enterUsername, enterPass);
			} catch (IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
			/* Якщо включена позначка "Enter as Admin" */
			if(asAdmin) {
				session.setAttribute("greeting", MessageManager.getProperty("message.welcomeAdmin") + user.getFirstName() + "!");
				session.setAttribute("isAdmin", asAdmin);
				session.setAttribute("isLoggedIn", true);
				
				Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionData");
				if(sessionData == null) {
					sessionData = new HashMap<>();
					session.setAttribute("sessionData", sessionData);
				}
				
				sessionData.put(windowId, user);
				
				obj.put("windowId", windowId);
				
				msg = "1";
				obj.put("msg", msg);
				list.add(obj);
				out.println(list.toJSONString());
				out.flush();
			} else {                                          /* Якщо вимкнена позначка "Enter as Admin" */
				msg = "3";
				obj.put("msg", msg);
				list.add(obj);
				out.println(list.toJSONString());
				out.flush();
			}
		} else {                                              /* Якщо адміністратор не був знайдений - переходимо до пошуку користувача */
			if(asAdmin) {
				msg = "5";
				obj.put("msg", msg);
				list.add(obj);
				out.println(list.toJSONString());
				out.flush();
			} else {
				/*
				 * Реалізація логіки входу звичайного користувача в систему
				 * Якщо користувач був знайдений - вхід в систему
				 * В іншому випадку - повідомлення помилки
				 */
				user = daoUser.findEntityByTwoParameters(enterUsername, enterPass);
				if(user != null) {
					session.setAttribute("greeting", MessageManager.getProperty("message.welcomeUser") + user.getFirstName() + "!");
					session.setAttribute("isAdmin", asAdmin);
					session.setAttribute("isLoggedIn", true);
					
					Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionData");
					if(sessionData == null) {
						sessionData = new HashMap<>();
						session.setAttribute("sessionData", sessionData);
					}
					
					sessionData.put(windowId, user);
					
					obj.put("windowId", windowId);
					
					msg = "7";
					obj.put("msg", msg);
					list.add(obj);
					out.println(list.toJSONString());
					out.flush();
				} else {
					msg = "9";
					obj.put("msg", msg);
					list.add(obj);
					out.println(list.toJSONString());
					out.flush();
				}
			}
		}
	}

}
