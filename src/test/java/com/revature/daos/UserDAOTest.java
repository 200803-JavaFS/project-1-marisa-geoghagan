package com.revature.daos;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.models.User;

public class UserDAOTest {
	public static UserDAO uDao = new UserDAO();
	
	//@Test
	public void testInsert() {
		User u1 = new User("admin", "password", "Elaine", "Smith", "esmith@gmail.com", 1);
		assertTrue(uDao.insert(u1));
	}
	
	//@Test
	public void testUpdate() {
		User u1 = new User(1, "admin", "password", "Elaine", "Smith", "esmith@gmail.com", 2);
		assertTrue(uDao.update(u1));
	}
}