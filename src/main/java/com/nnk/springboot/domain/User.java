package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Entity User.
 * 
 * @author Antoine Lanselle
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="username")
	@NotBlank(message = "Username is mandatory")
	private String username;
	
	@Column(name="password")
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Column(name="fullname")
	@NotBlank(message = "FullName is mandatory")
	private String fullname;
	
	@Column(name="role")
	@NotBlank(message = "Role is mandatory")
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
