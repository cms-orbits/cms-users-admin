package com.cms.users.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;



/**
* The persistent class for the USERS database table.
* 
*/
@Entity
@EntityListeners(UserBase.class)
@Table(name="Contests")
public class Contest implements Serializable {
	private static final long serialVersionUID = 1L;

	public Contest() {
	}
	
	@Id
	private long id;
	
	private String name;
	
	private String description;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
