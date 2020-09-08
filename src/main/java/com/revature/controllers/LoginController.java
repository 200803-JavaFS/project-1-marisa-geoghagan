package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.services.LoginService;

public class LoginController {
	private static LoginService ls = new LoginService();
	private static ObjectMapper om = new ObjectMapper();
	private User u;

	public boolean login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		if (req.getMethod().equals("POST")) {
			BufferedReader reader = req.getReader();
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			String body = new String(sb);
			LoginDTO ldto = om.readValue(body, LoginDTO.class);
			u = ls.login(ldto);

			if (u != null) {
				HttpSession ses = req.getSession();
				ses.setAttribute("loggedin", true);
				res.setStatus(200);
				res.getWriter().println("Login Successful");
				return true;
			} else {
				HttpSession ses = req.getSession(false);
				if (ses != null)
					ses.invalidate();
				res.setStatus(401);
				res.getWriter().println("Login failed");
				return false;
			}
		} else {
			res.getWriter().println("Unhappy program - stop trying to do bad things!");
			return false;
		}
	}

	public boolean logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession ses = req.getSession();

		ses.invalidate();
		res.setStatus(200);
		res.getWriter().println("Successful logout!");
		return true;
	}
	
	public User getUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
		if (req.getMethod().equals("GET")) {
			res.setStatus(200);
			String json = om.writeValueAsString(u);
			res.getWriter().println(json);
			return u;
		} else {
			res.getWriter().println("Unexpected request!");
			return null;
		}
	}
	
	public void getUsers(HttpServletResponse res) throws IOException {
		List<User> list = ls.findAll();
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}
}