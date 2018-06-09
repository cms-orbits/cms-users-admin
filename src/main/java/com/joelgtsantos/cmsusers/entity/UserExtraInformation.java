package com.joelgtsantos.cmsusers.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
* The persistent class for the USERS database table.
* 
*/
@Entity
@Table(name="USER_EXTRA_INFORMATION")
public class UserExtraInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserExtraInformation() {
	}
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_EXTRA_INFO_SEQ")
    @SequenceGenerator(sequenceName = "user_extra_information_seq", allocationSize = 1, name = "USER_EXTRA_INFO_SEQ")
	private long id;*/
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthdate;
	
	private String study;
	
	private String work;
	
	@Column(name="want_work")
	private String wantWork;
	
	@Id
	@Column(name="user_id")
	private long id;
	
	private byte[] cv;
	
	private String name;
	
	/*public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}*/

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	
	
	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getWantWork() {
		return wantWork;
	}

	public void setWantWork(String wantWork) {
		this.wantWork = wantWork;
	}

	public byte[] getCv() {
		return cv;
	}

	public void setCv(byte[] cv) {
		this.cv = cv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
