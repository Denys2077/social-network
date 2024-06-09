package com.denys.dw.content;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionRequestContent {

	 private HashMap<String, Object> requestAttributes = new HashMap<>();
	    private HashMap<String, String[]> requestParameters = new HashMap<>();
	    private HashMap<String, Object> sessionAttributes = new HashMap<>();

	    public SessionRequestContent() {
	    	
	    }

	    public void extractValues(HttpServletRequest request) {
	    	
	        requestParameters.putAll(request.getParameterMap());

	        
	        request.getAttributeNames().asIterator().forEachRemaining(name -> 
	            requestAttributes.put(name, request.getAttribute(name))
	        );

	        
	        HttpSession session = request.getSession(false);
	        if (session != null) {
	            session.getAttributeNames().asIterator().forEachRemaining(name -> 
	                sessionAttributes.put(name, session.getAttribute(name))
	            );
	        }
	    }

	    public void insertAttributes(HttpServletRequest request) {
	    	
	        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
	            request.setAttribute(entry.getKey(), entry.getValue());
	        }

	        HttpSession session = request.getSession();
	        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
	            session.setAttribute(entry.getKey(), entry.getValue());
	        }
	    }

	    // Some methods
	    
	    public HashMap<String, Object> getRequestAttributes() {
	        return requestAttributes;
	    }

	    public HashMap<String, String[]> getRequestParameters() {
	        return requestParameters;
	    }

	    public HashMap<String, Object> getSessionAttributes() {
	        return sessionAttributes;
	    }
	
}
