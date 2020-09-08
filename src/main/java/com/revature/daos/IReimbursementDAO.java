package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface IReimbursementDAO {
	public boolean update(Reimbursement r);
	public List<Reimbursement> findPending();
	public List<Reimbursement> findApproved();
	public List<Reimbursement> findDenied();
	public List<Reimbursement> findAll();
	public Reimbursement findByID(int id);
	public List<Reimbursement> findByAuthor(int author);
	public boolean addReimbursement(Reimbursement r);
}
