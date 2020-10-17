package com.joelgtsantos.cmsusers.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.LoggerFactory;

import com.joelgtsantos.cmsusers.service.utilities.Crypto;

public class UserBase {
	public static org.slf4j.Logger log = LoggerFactory.getLogger(Participation.class);

	/*
	 * This method creates a new encrypted password Using as main key the string in
	 * the cms-config file
	 * 
	 */
	@PrePersist
	public void userPrePersist(User user) {	
		user.setPassword(Crypto.encryptPlain(user.getPassword().replaceAll("plaintext:", "")));
	}
	
	@PreUpdate
	public void userPreUpdate(User user) {
		user.setPassword(Crypto.encryptPlain(user.getPassword().replaceAll("plaintext:", "")));
	}
}
