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
import com.denys.dw.user.addition.Address;

@SuppressWarnings("all")
@WebServlet("/updateAddress")
public class UpdateAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static final String COUTRY = "country",
			                    CITY = "city",
			                    STREET = "street",
			                    USER_ID = "user_id";
	
	private AbstractDAO<Address> dao = new AddressDAO();
	
    public UpdateAddress() {
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
    	
    	Address address = getAddress(request);
    	
		String msg;

		if(dao.updateEntityByParameter(address)) {
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

    private Address getAddress(HttpServletRequest request) {
    	Address address = new Address();
    	
    	address.setCountry(request.getParameter(COUTRY).trim());
    	address.setCity(request.getParameter(CITY).trim());
    	address.setStreet(request.getParameter(STREET).trim());
    	address.setUser_id(Long.parseLong(request.getParameter(USER_ID).trim()));

    	return address;
    }
}
