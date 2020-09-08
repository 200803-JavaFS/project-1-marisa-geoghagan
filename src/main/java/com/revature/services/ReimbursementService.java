package com.revature.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.ReimbursementDAO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.User;

public class ReimbursementService {
	private static final Logger log = LogManager.getLogger(LoginService.class);
	private static ReimbursementDAO rdao = new ReimbursementDAO();
	
	public List<Reimbursement> findPending() {
		log.debug("Fetching all pending reimbursement objects...");
		return rdao.findPending();
	}
	
	public List<Reimbursement> findApproved() {
		log.debug("Fetching all approved reimbursement objects...");
		return rdao.findApproved();
	}
	
	public List<Reimbursement> findDenied() {
		log.debug("Fetching all denied reimbursement objects...");
		return rdao.findDenied();
	}
	
	public List<Reimbursement> findAll() {
		log.debug("Fetching all reimbursement objects...");
		return rdao.findAll();
	}

	public boolean addReimbursement(ReimbursementDTO rdto) {
		Reimbursement r = new Reimbursement(rdto.amount, rdto.submitted, rdto.description, rdto.author, rdto.statusID, rdto.typeID);
		return rdao.addReimbursement(r);
	}

	public List<Reimbursement> findByAuthor(int author) {
		log.debug("Fetching all reimbursements by " + author);
		return rdao.findByAuthor(author);
	}
	
	public boolean approve(int id, User resolver) {
		log.debug("Fetching reimbursement with the id " + id);
		Reimbursement r = rdao.findByID(id);
		r.setResolved(new Timestamp(new Date().getTime()));
		r.setResolver(resolver);
		r.setStatusID(3);
		return rdao.update(r);
	}
	
	public boolean deny(int id, User resolver) {
		log.debug("Fetching reimbursement with the id " + id);
		Reimbursement r = rdao.findByID(id);
		r.setResolved(new Timestamp(new Date().getTime()));
		r.setResolver(resolver);
		r.setStatusID(2);
		return rdao.update(r);
	}
	
	public boolean isInteger(String input) {
		try { 
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) { 
			log.debug("Input is not an integer.");
			return false; 
		} catch(NullPointerException e) {
			log.debug("Input is blank.");
			return false;
		}
	}
	
	public int stringToInt(String input) {
		if(isInteger(input)) {
			return Integer.parseInt(input);
		} else {
			log.warn("Something has gone seriously wrong.");
			return -1;
		}
	}
}