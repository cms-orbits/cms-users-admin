package com.cms.users.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public User() {
	}
	
	
//			id 						integer 			NOT NULL    nextval('users_id_seq'::regclass) 	[pk] 
//			first_name 				character varying 	NOT NULL
//			last_name 				character varying 	NOT NULL
//			username 				character varying 	NOT Null										[check][uniq]
//			password 				character varying 	NOT NULL
//			email 					character varying 	
//			timezone 				character varying 	
//			preferred_languages 	character varying 	NOT NULL
	
	@Id
	private long id;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String timezone;
	
	@Column(name="preferred_languages")
	private String preferredLanguages;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setFirstName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUserame(String username) {
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

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getPreferredLanguages() {
		return preferredLanguages;
	}

	public void setPreferredLanguages(String preferredLanguages) {
		this.preferredLanguages = preferredLanguages;
	}
	
}
