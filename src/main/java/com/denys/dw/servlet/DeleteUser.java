package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.AddressDAO;
import com.denys.dw.dao.BirthDAO;
import com.denys.dw.dao.ContactInfoDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;
import com.denys.dw.user.addition.Address;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.ContactInfo;

@WebServlet("/deleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USER_ID = "user_id";
	
	private AbstractDAO<User> daoUser = new UserDAO();
	private AbstractDAO<ContactInfo> daoInfo = new ContactInfoDAO();
	private AbstractDAO<Address> daoAddress = new AddressDAO();
	private AbstractDAO<Birth> daoBirth = new BirthDAO();
	
    public DeleteUser() {
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
		User user = daoUser.findEntityById(getUserId(request));
		ContactInfo info = daoInfo.findEntityById(user.getId());
		Address address = daoAddress.findEntityById(user.getId());
		Birth birth = daoBirth.findEntityById(user.getId());
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		String msg;
		
		if(daoInfo.deleteEntityByParameter(info) && daoAddress.deleteEntityByParameter(address) && daoBirth.deleteEntityByParameter(birth) && daoUser.deleteEntityByParameter(user)) {
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
	
	private long getUserId(HttpServletRequest request) {
		return Long.parseLong(request.getParameter(USER_ID));
	}
}