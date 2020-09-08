package com.revature.models;

public class LoginDTO {
	public String username;
	public String password;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
}