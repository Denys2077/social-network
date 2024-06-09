package com.denys.dw.logic;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.UserDAO;
import com.denys.dw.user.User;

public class RegistrationLogic {

	private static AbstractDAO<User> dao = new UserDAO();
	
	public static boolean checkRegister(String username, String password) {
		User existingUser = dao.findEntityByTwoParameters(username, password);
		return (existingUser == null) ? false : true;
	}
}
