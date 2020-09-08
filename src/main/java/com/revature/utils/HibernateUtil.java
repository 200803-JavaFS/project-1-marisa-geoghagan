package com.revature.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.revature.daos.UserDAO;

public class HibernateUtil {
	private static final Logger log = LogManager.getLogger(UserDAO.class);
	private static Session ses;
	private static SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	public static Session getSession() {
		if(ses==null) {
			ses=sf.openSession();
			log.debug("New Session created.");
		}
		log.debug("Session returned.");
		return ses;
	}
	
	public static void closeSes() {
		ses.close();
		ses = null;
		log.debug("Session closed.");
	}
}
