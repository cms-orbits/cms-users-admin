package com.cms.users.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



/**
* The persistent class for the USERS database table.
* 
*/
@Entity
@EntityListeners(UserBase.class)
@Table(name="USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public User() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "users_id_seq", allocationSize = 1, name = "USER_SEQ")
	private long id;
	
	@Transient
	private String redirect;

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
	
	@JsonProperty(access = Access.WRITE_ONLY)
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
	
	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

}
