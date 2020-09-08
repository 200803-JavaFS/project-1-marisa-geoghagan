package com.revature.daos;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.models.User;

public class UserDAOTest {
	public static UserDAO uDao = new UserDAO();
	
	//@Test
	public void testInsert() {
		User u1 = new User("newUser", "password", "Jane", "Doe", "jdoe@gmail.com", 2);
		assertTrue(uDao.insert(u1));
	}
	
	//@Test
	public void testUpdate() {
		User u1 = new User(1, "admin", "password", "Elaine", "Smith", "esmith@gmail.com", 2);
		assertTrue(uDao.update(u1));
	}
	
	//@Test
	public void TestSelectById(int userID) {
		User u1 = new User(1, "admin", "password", "Elaine", "Smith", "esmith@gmail.com", 1);
		assertTrue(uDao.selectById(1).equals(u1));
	}
	
	@Test
	public void TesthashPassword() {
		User u1 = uDao.selectById(2);
		u1.setPassword(Integer.toString(u1.hashCode()));
		assertTrue(uDao.update(u1));
	}
}