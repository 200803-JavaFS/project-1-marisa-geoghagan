package com.revature.web;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.controllers.LoginController;
import com.revature.controllers.ReimbursementController;
import com.revature.models.User;

public class MasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(MasterServlet.class);
	private static LoginController lc = new LoginController();
	private static ReimbursementController rc = new ReimbursementController();
	private User loggedIn;

	public MasterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);

		final String URI = req.getRequestURI().replace("/Project1/", "");

		String[] portions = URI.split("/");

		System.out.println(Arrays.toString(portions));
		if(portions.length==0) {
			req.getRequestDispatcher("index.html").forward(req, res);
		}
		try {
			switch (portions[0]) {
				case "login":
					log.info("Trying to log in...");
					lc.login(req, res);
					break;
				case "logout":
					log.info("Trying to log out...");
					lc.logout(req, res);
					loggedIn = null;
					break;
				case "getUser":
					log.info("Grabbing the user...");
					loggedIn = lc.getUser(req, res);
					break;
				case "viewPending":
					log.info("Viewing all pending reimbursements...");
					rc.viewPending(res);
					break;
				case "viewApproved":
					log.info("Viewing all approved reimbursements...");
					rc.viewApproved(res);
					break;
				case "viewDenied":
					log.info("Viewing all denied reimbursements...");
					rc.viewDenied(res);
					break;
				case "viewAll":
					log.info("Viewing all reimbursements...");
					rc.viewAll(res);
					break;
				case "approve":
					log.info("Attempting to approve a request...");
					rc.approve(req, res, loggedIn);
					break;
				case "deny":
					log.info("Attempting to deny a request...");
					rc.deny(req, res, loggedIn);
					break;
				case "getUsers":
					log.info("Grabbing a list of all users...");
					lc.getUsers(res);
					break;
				case "viewHistory":
					log.info("Grabbing a list of requests by user...");
					rc.getRequests(req, res);
					break;
				case "ownHistory":
					log.info("Grabbing a list of requests by self...");
					rc.getOwnRequests(req, res);
					break;
				case "addReimbursement":
					log.info("Adding new Reimbursement Object...");
					rc.addReimbursement(req, res);
					break;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			res.getWriter().print("The id you provided is not an integer");
			res.setStatus(400);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}