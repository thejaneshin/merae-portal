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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message=" is required")
	@Size(min = 1, message=" is required")
	@Column(name="company")
	private String company;
	
	@Column(name="paid")
	private boolean paid;
	
	@NotNull(message=" is required")
	@Size(min = 1, message=" is required")
	@Column(name="invoice")
	private String invoice;
	
	@Column(name="assigned_date")
	private LocalDate assignedDate;
	
	@Column(name="type")
	private String type;
	
	@NotNull(message=" is required")
	@Size(min = 1, message=" is required")
	@Column(name="description")
	private String description;
	
	@Column(name="print")
	private boolean print;
	
	@Column(name="status")
	private String status;
	
	@NotNull(message=" is required")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="due_date")
	private LocalDate dueDate;
	
	@Column(name="submitted_date")
	private LocalDate submittedDate;
	
	@Column(name="cancelled_date")
	private LocalDate cancelledDate;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	public Project() {
		this.status = "N/A";
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
		this.status = "N/A";
	}

	public Project(int id, String company, boolean paid, String invoice, LocalDate assignedDate, String type,
			String description, boolean print, String status, LocalDate dueDate, LocalDate submittedDate,
			LocalDate cancelledDate, User user) {
		this.id = id;
		this.company = company;
		this.paid = paid;
		this.invoice = invoice;
		this.assignedDate = assignedDate;
		this.type = type;
		this.description = description;
		this.print = print;
		this.status = status;
		this.dueDate = dueDate;
		this.submittedDate = submittedDate;
		this.cancelledDate = cancelledDate;
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

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LocalDate getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(LocalDate cancelledDate) {
		this.cancelledDate = cancelledDate;
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
				+ print + ", status=" + status + ", dueDate=" + dueDate + ", submittedDate=" + submittedDate
				+ ", cancelledDate=" + cancelledDate + ", user=" + user + "]";
	}

}
