package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name="ers_reimbursement")
public class Reimbursement implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(Reimbursement.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reim_id")
	private int reimId;
	
	@Column(name="reimb_amount", nullable=false)
	private double amount;
	
	@Column(name="reimb_submitted", nullable=false)
	private Timestamp submitted;

	@Column(name="reimb_resolved")
	private Timestamp resolved;
	
	@Column(name="reimb_description")
	private String description;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="reimb_author", referencedColumnName="ers_user_id", nullable=false)
	private User author;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="reimb_resolver", referencedColumnName="ers_user_id")
	private User resolver;
	
	@Column(name="reimb_status_id", nullable=false)
	private int statusID;
	
	@Column(name="reimb_type_id", nullable=false)
	private int typeID;
	
	public Reimbursement() {
		super();
		log.debug("Blank Reimbursement Object created.");
	}

	public Reimbursement(double amount, Timestamp submitted, Timestamp resolved, String description, User author, User resolver, int statusID, int typeID) {
		super();
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusID = statusID;
		this.typeID = typeID;
		log.debug("No-ID Reimbursement Object created.");
	}
	
	public Reimbursement(double amount, Timestamp submitted, String description, User author, int statusID, int typeID) {
		this.amount = amount;
		this.submitted = submitted;
		this.description = description;
		this.author = author;
		this.statusID = statusID;
		this.typeID = typeID;
		log.debug("New Reimbursement Object created.");
	}
	
	public Reimbursement(int reimId, double amount, Timestamp submitted, Timestamp resolved, String description, User author, User resolver, int statusID, int typeID) {
		super();
		this.reimId = reimId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusID = statusID;
		this.typeID = typeID;
		log.debug("Full Reimbursement Object created.");
	}

	public int getId() {
		return reimId;
	}

	public void setId(int reimId) {
		this.reimId = reimId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimId=" + reimId + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved + ", description=" + description + ", author=" + author + ", resolver=" + resolver + ", statusID=" + statusID + ", typeID=" + typeID + "]";
	}
}