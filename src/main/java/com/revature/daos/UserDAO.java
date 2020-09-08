package com.revature.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class UserDAO implements IUserDAO {
	private static final Logger log = LogManager.getLogger(UserDAO.class);
	
	public UserDAO() {
		super();
		log.debug("Blank UserDAO Object created.");
	}
	
	@Override
	public boolean insert(User user) {
		Session ses = HibernateUtil.getSession();			
		try{
			ses.save(user);
			return true;
		} catch(HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(User user) {
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		try{
			ses.merge(user);
			tx.commit();
			return true;
		} catch(HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
	}

	@Override
	public boolean delete(int userID) {
		Session ses = HibernateUtil.getSession();
		try {
			ses.createQuery("DELETE FROM User WHERE userID=" + userID);
			return true;
		} catch(HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User selectById(int userID) {
		Session ses = HibernateUtil.getSession();
		return ses.get(User.class, userID);
	}

	@Override
	public User selectByUsername(String username) {
		Session ses = HibernateUtil.getSession();
		return (User) ses.createQuery("FROM User U WHERE U.username = :userName").setParameter("userName", username).uniqueResult();
	}

	@Override
	public List<User> selectByRole(int roleID) {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM User WHERE role=" + roleID, User.class).list();
	}

	@Override
	public List<User> selectAll() {
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM User").list();
	}
}