package com.revature.daos;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import com.revature.models.Reimbursement;

public class ReimbursementDAOTest {
	public static ReimbursementDAO rDao = new ReimbursementDAO();
	public static UserDAO uDao = new UserDAO();
	
	@Test
	public void testInsert() {
		Reimbursement r1 = new Reimbursement(10.00,  new Timestamp(new Date().getTime()), "Frivolous Expense", uDao.selectById(2), 2, 4);
		assertTrue(rDao.addReimbursement(r1));
	}
}