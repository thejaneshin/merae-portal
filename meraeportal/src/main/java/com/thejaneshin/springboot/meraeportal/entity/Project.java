package com.thejaneshin.springboot.meraeportal.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="company")
	private String company;
	
	@Column(name="paid")
	private boolean paid;
	
	@Column(name="invoice")
	private String invoice;
	
	@Column(name="assigned_date")
	private LocalDate assignedDate;
	
	@Column(name="type")
	private String type;
	
	@Column(name="description")
	private String description;
	
	@Column(name="print")
	private boolean print;
	
	@Column(name="status")
	private String status;
	
	@Column(name="due_date")
	private LocalDate dueDate;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	public Project() {
		
	}

	public Project(String company, boolean paid, String invoice, String type, String description, boolean print,
			LocalDate dueDate) {
		this.company = company;
		this.paid = paid;
		this.invoice = invoice;
		this.type = type;
		this.description = description;
		this.print = print;
		this.dueDate = dueDate;
	}

	public Project(String company, boolean paid, String invoice, LocalDate assignedDate, String type,
			String description, boolean print, String status, LocalDate dueDate, User user) {
		this.company = company;
		this.paid = paid;
		this.invoice = invoice;
		this.assignedDate = assignedDate;
		this.type = type;
		this.description = description;
		this.print = print;
		this.status = status;
		this.dueDate = dueDate;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", company=" + company + ", paid=" + paid + ", invoice=" + invoice
				+ ", assignedDate=" + assignedDate + ", type=" + type + ", description=" + description + ", print="
				+ print + ", status=" + status + ", dueDate=" + dueDate + ", user=" + user + "]";
	}
	
}
