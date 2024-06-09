package com.denys.dw.logic;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.denys.dw.sax.read.SAXAdministratorsHandler;
import com.denys.dw.user.User;

public class LoginLogic {
	
	public static boolean checkLogin(String enterUsername, String enterPassword) {
		User user = null;
		try {
			user = getUser(enterUsername, enterPassword);
		} catch (IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return (user != null) ? true : false;
	}

	public static User getUser(String enterUsername, String enterPassword) throws IOException, ParserConfigurationException {
		final String filepath = "D:\\Java_Advanced\\DiplomaWork\\src\\resources\\XML\\administrators.xml";
		SAXAdministratorsHandler sax = new SAXAdministratorsHandler();
		List<User> data = sax.getAllAdministrators(filepath);
		return data.stream().filter(user -> user.getUsername().equals(enterUsername) 
				&& user.getPassword().equals(enterPassword)).findFirst().orElse(null);
	}
}
