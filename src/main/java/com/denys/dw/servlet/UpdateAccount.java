package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2 мб
maxFileSize = 1024*1024*10,  // 10мб
maxRequestSize = 1024*1024*50)
@WebServlet("/updateAccount")
public class UpdateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String ID = "user_id",
			                    USERNAME = "username",
			                    PASSWORD = "password",
			                    FIRST_NAME = "firstName",
			                    LAST_NAME = "lastName",
			                    PHOTO = "inputPhoto";
	
	private AbstractDAO<User> dao = new UserDAO();
	
    public UpdateAccount() {
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

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		User user = getUser(request, response);
		String msg;
		
		if(dao.updateEntityByParameter(user)) {
			msg = "1";
			obj.put("msg", msg);
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
		} else {
			msg = "3";
			obj.put("msg", msg);
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
		}
	}
	
	private User getUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		User user = null;
		Long userId = Long.parseLong(request.getParameter(ID));
		String username = request.getParameter(USERNAME).trim();
		String password = request.getParameter(PASSWORD).trim();
		String firstname = request.getParameter(FIRST_NAME).trim();
		String lastname = request.getParameter(LAST_NAME).trim();
		Part file = request.getPart(PHOTO);
		InputStream inputStream = null;
		if(file != null) {
			inputStream = file.getInputStream();
		}
		boolean isAdmin = false;
		user = new User(userId, username, firstname, lastname, password, null, isAdmin);
		if(inputStream != null) {
			user.setInputStreamPhoto(inputStream);
		}
		return user;
	}

}
