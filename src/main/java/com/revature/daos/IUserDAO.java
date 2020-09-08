package com.revature.daos;

import java.util.List;

import com.revature.models.User;

public interface IUserDAO {
	public boolean insert(User user);
	public boolean update(User user);
	public boolean delete(int userID);
	public User selectById(int userID);
	public User selectByUsername(String username);
	public List<User> selectByRole(int roleID);
	public List<User> selectAll();
}
