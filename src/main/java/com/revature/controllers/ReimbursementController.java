package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementID;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.ReimbursementService;

public class ReimbursementController {
	private static ReimbursementService rs = new ReimbursementService();
	private static ObjectMapper om = new ObjectMapper();
	
	public void viewPending(HttpServletResponse res) throws IOException {
		List<Reimbursement> list = rs.findPending();
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}
	
	public void viewApproved(HttpServletResponse res) throws IOException {
		List<Reimbursement> list = rs.findApproved();
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}
	
	public void viewDenied(HttpServletResponse res) throws IOException {
		List<Reimbursement> list = rs.findDenied();
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}
	
	public void viewAll(HttpServletResponse res) throws IOException {
		List<Reimbursement> list = rs.findAll();
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}

	public void getRequests(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		UserDTO u = om.readValue(body, UserDTO.class);
		System.out.println(u.userID);
		int author = rs.stringToInt(u.userID);
		List<Reimbursement> list = rs.findByAuthor(author);
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}
	
	public boolean approve(HttpServletRequest req, HttpServletResponse res, User resolver) throws IOException {
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		ReimbursementID r = om.readValue(body, ReimbursementID.class);
		System.out.println(r.reimID);
		int id = rs.stringToInt(r.reimID);
		res.setStatus(200);
		return rs.approve(id, resolver);
	}
	
	public boolean deny(HttpServletRequest req, HttpServletResponse res, User resolver) throws IOException {
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		ReimbursementID r = om.readValue(body, ReimbursementID.class);
		System.out.println(r.reimID);
		int id = rs.stringToInt(r.reimID);
		res.setStatus(200);
		return rs.deny(id, resolver);
	}
	
	public void addReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();		
		StringBuilder sb = new StringBuilder();		
		String line = reader.readLine();
		
		while(line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		
		String body = new String(sb);
		res.getWriter().println(body);
		ReimbursementDTO rdto = om.readValue(body, ReimbursementDTO.class);
		System.out.println(rdto);
		
		if (rs.addReimbursement(rdto)) {
			res.setStatus(201);
			res.getWriter().println("Reimbursement was created");
		} else {
			res.setStatus(403);
		}
	}
	
	public void getOwnRequests(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		String body = new String(sb);
		User u = om.readValue(body, User.class);
		List<Reimbursement> list = rs.findByAuthor(u.getUserID());
		res.getWriter().println(om.writeValueAsString(list));
		res.setStatus(200);
	}
}