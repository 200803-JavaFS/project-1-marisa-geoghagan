package com.revature.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimbursement;
import com.revature.utils.HibernateUtil;

public class ReimbursementDAO implements IReimbursementDAO {
	private static final Logger log = LogManager.getLogger(ReimbursementDAO.class);
	
	public ReimbursementDAO() {
		super();
		log.debug("Blank ReimbursementDAO Object created.");
	}
	
	@Override
	public boolean update(Reimbursement r) {
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		try{
			ses.merge(r);
			tx.commit();
			return true;
		} catch(HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
	}
	
	@Override
	public List<Reimbursement> findPending() {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM Reimbursement WHERE statusID=1", Reimbursement.class).list();
	}
	
	@Override
	public List<Reimbursement> findApproved() {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM Reimbursement WHERE statusID=3", Reimbursement.class).list();
	}
	
	@Override
	public List<Reimbursement> findDenied() {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM Reimbursement WHERE statusID=2", Reimbursement.class).list();
	}
	
	@Override
	public List<Reimbursement> findAll() {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM Reimbursement").list();
	}
	
	@Override
	public Reimbursement findByID(int id) {
		Session ses = HibernateUtil.getSession();
		return ses.get(Reimbursement.class, id);
	}
	
	@Override
	public List<Reimbursement> findByAuthor(int author) {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM Reimbursement WHERE author.userID=" + author, Reimbursement.class).list();
	}

	@Override
	public boolean addReimbursement(Reimbursement r) {
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			ses.save(r);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
	}	
}