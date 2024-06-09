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
import com.denys.dw.dao.ContactInfoDAO;
import com.denys.dw.user.addition.ContactInfo;

@SuppressWarnings("all")
@WebServlet("/updateContactInfo")
public class UpdateContactInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String EMAIL = "email",
			                    TELEPHONE = "telephone",
			                    DEPARTMENT = "department",
			                    POSITION = "position",
			                    USER_ID = "user_id";
	
	private AbstractDAO<ContactInfo> dao = new ContactInfoDAO();
	
    public UpdateContactInfo() {
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
    	JSONObject obj = new JSONObject();
    	JSONArray list = new JSONArray();
    	PrintWriter out = response.getWriter();
    	
    	ContactInfo info = getContactInfo(request);
    	
    	String msg;
    	
    	if(dao.updateEntityByParameter(info)) {
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
    
    private ContactInfo getContactInfo(HttpServletRequest request) {
    	ContactInfo info = new ContactInfo();
    	
    	String email = request.getParameter(EMAIL).trim();
    	String telephone = request.getParameter(TELEPHONE).trim();
    	String position = request.getParameter(POSITION).trim();
    	String department = request.getParameter(DEPARTMENT).trim();
    	Long user_id = Long.parseLong(request.getParameter(USER_ID).trim());
    	
    	info.setEmail(email);
    	info.setTelephone(telephone);
    	info.setPosition(position);
    	info.setDepartment(department);
    	info.setUser_id(user_id);
    	
    	return info;
    }
}
