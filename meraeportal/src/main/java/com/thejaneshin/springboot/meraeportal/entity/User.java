package com.thejaneshin.springboot.meraeportal.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.thejaneshin.springboot.meraeportal.validation.ValidEmail;
import com.thejaneshin.springboot.meraeportal.validation.ValidPhone;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message=" is required")
	@Size(min=1, message=" is required")
	@Column(name="username")
	private String username;
	
	@NotNull(message=" is required")
	@Size(min=1, message=" is required")
	@Column(name="password")
	private String password;
	
	@ValidEmail
	@Column(name="email")
	private String email;
	
	@ValidPhone
	@Column(name="phone")
	private String phone;
	
	@NotNull(message=" is required")
	@Size(min=1, message=" is required")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull(message=" is required")
	@Size(min=1, message=" is required")
	@Column(name="last_name")
	private String lastName;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="user_role", 
	joinColumns=@JoinColumn(name="user_id"), 
	inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	@OneToMany(mappedBy="user",
			   fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE,
			   CascadeType.DETACH, CascadeType.REFRESH})
	private Set<Project> projects;
	
	public User() {
		
	}

	public User(String username, String password, String email, String phone, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String username, String password, String email, String phone, String firstName, String lastName,
			Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setCustomers(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phone="
				+ phone + ", firstName=" + firstName + ", lastName=" + lastName + ", roles=" + roles + "]";
	}
	
}
