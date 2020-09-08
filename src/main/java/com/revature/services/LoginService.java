package com.revature.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.UserDAO;
import com.revature.models.LoginDTO;
import com.revature.models.User;

public class LoginService {
	private static final Logger log = LogManager.getLogger(LoginService.class);
	private static UserDAO udao = new UserDAO();
		
	public User login(LoginDTO ldto) {
		User u = udao.selectByUsername(ldto.username);
		log.debug("The hashed password of the user in the database is " + u.getPassword()); // I know this is a bad thing to do.
		log.debug("The hashed password of the inputted user is " + ldto.hashCode());
		if(u.getPassword().equals(Integer.toString(ldto.hashCode()))) {
			log.info("Login sucessful!");
			return u;
		} else{
			log.error("Incorrect password.");
			return null;
		}
	}
	
	public List<User> findAll() {
		return udao.selectAll();
	}
}