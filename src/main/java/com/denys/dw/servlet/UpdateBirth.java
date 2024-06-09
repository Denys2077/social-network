package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.BirthDAO;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.Sex;

@SuppressWarnings("all")
@WebServlet("/updateBirth")
public class UpdateBirth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String SEX = "gender",
			                    DATE = "date",
			                    USER_ID = "user_id";
	
	private AbstractDAO<Birth> dao = new BirthDAO();
	
    public UpdateBirth() {
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
    	Birth birth = getBirth(request);
    	
    	JSONArray list = new JSONArray();
    	JSONObject obj = new JSONObject();
    	PrintWriter out = response.getWriter();
    	
    	String msg;
    
    	if(dao.updateEntityByParameter(birth)) {
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
    
    private Birth getBirth(HttpServletRequest request) {
    	Birth birth = new Birth();
    	
    	String birthString = request.getParameter(DATE).trim();
    	Sex sex = Sex.valueOf(request.getParameter(SEX).trim().toUpperCase());
    	Long user_id = Long.parseLong(request.getParameter(USER_ID));
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate date = LocalDate.parse(birthString, formatter);
	    
	    birth.setSex(sex);
	    birth.setDate(date);
	    birth.setUser_id(user_id);
	    
    	return birth;
    }

}
