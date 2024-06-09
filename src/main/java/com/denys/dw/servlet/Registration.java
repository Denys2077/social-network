package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.logic.RegistrationLogic;
import com.denys.dw.user.User;
import com.denys.dw.user.addition.Address;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.ContactInfo;
import com.denys.dw.user.addition.Sex;

@WebServlet("/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USERNAME = "username",
            FIRSTNAME = "firstName",
            LASTNAME = "lastName",
            PASSWORD = "password",
            EMAIL = "email",
            TELEPHONE = "telephone",
            POSITION = "position",
            DEPARTMENT = "department",
            COUNTRY = "country",
            CITY = "city",
            STREET = "street",
            BIRTH = "date",
            GENDER = "gender";

private AbstractDAO<User> dao = new UserDAO();
       
    public Registration() {
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
	
		PrintWriter out = response.getWriter();
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		String msg;
		
		User user = createUser(request);
		
		if(!RegistrationLogic.checkRegister(user.getUsername(), user.getPassword())) {
			if(dao.createEntityByParameter(user)) {
				msg = "1";
				obj.put("msg", msg);
				list.add(obj);
				out.println(list.toJSONString());
				out.flush();
			}
		} else {
			msg = "3";
			obj.put("msg", msg);
			list.add(obj);
			out.println(list.toJSONString());
			out.flush();
		}
		
	}
	
    private User createUser(HttpServletRequest request) {
		
		String username = request.getParameter(USERNAME).trim();
		String firstname = request.getParameter(FIRSTNAME).trim();
		String lastname = request.getParameter(LASTNAME).trim();
		String password = request.getParameter(PASSWORD).trim();
		Blob photo = null;
		boolean isAdmin = false;
		
		User user = new User(username, firstname, lastname, password, photo, isAdmin);
		
		if(createContInfo(request) != null) {
			user.setContactInfo(createContInfo(request));
		}
		if(createAddress(request) != null) {
			user.setAddress(createAddress(request));
		}
		if(createBirth(request) != null) {
			user.setBirth(createBirth(request));
		}
		return user;
	}

	private ContactInfo createContInfo(HttpServletRequest request) {
		
		String email = request.getParameter(EMAIL).trim();
		String telephone = request.getParameter(TELEPHONE).trim();
		String position = request.getParameter(POSITION).trim();
		String department = request.getParameter(DEPARTMENT).trim();
		
		ContactInfo info = new ContactInfo();
		
		info.setEmail(email);
		info.setTelephone(telephone);
		info.setPosition(position);
		info.setDepartment(department);
		
		return info;
	}
	
	private Address createAddress(HttpServletRequest request) {
		
		String country = request.getParameter(COUNTRY).trim();
		String city = request.getParameter(CITY).trim();
		String street = request.getParameter(STREET).trim();
		
		Address address = new Address();
		address.setCountry(country);
		address.setCity(city);
		address.setStreet(street);
		
		return address;
	}
	
	private Birth createBirth(HttpServletRequest request) {
		
		String birthString = request.getParameter(BIRTH);
		Sex gender = Sex.valueOf(request.getParameter(GENDER).toUpperCase());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate date = LocalDate.parse(birthString, formatter);
	    
	    Birth birth = new Birth();
	    
	    birth.setSex(gender);
	    birth.setDate(date);
	    
		return birth;
	}

}
