package com.revature.models;

import java.io.File;
import java.sql.Timestamp;

public class ReimbursementDTO {
	public double amount;
	public Timestamp submitted;
	public String description;
	public User author;
	public int statusID;
	public int typeID;
}
