package com.cms.users.entity;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.PrePersist;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;

public class UserBase {
	public static org.slf4j.Logger log = LoggerFactory.getLogger(Participation.class);
	
	
	/*
	 *This method creates a new encrypted password
	 *Using as main key the string in the cms-config file
	 * 
	 * */
	@PrePersist
	public void userPrePersist(User user) {
		String newPassword = "";
		
		try {
		String input = user.getPassword();
		String key = "8e045a51e4b102ea803c06f92841a1fb";
		String initVector = "RandomInitVector";//Random Key
		
		 IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		 SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		 cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		
		 byte[] encrypted = cipher.doFinal(input.getBytes());
		 byte[] c = new byte[initVector.getBytes("UTF-8").length + encrypted.length];
		 System.arraycopy(initVector.getBytes("UTF-8"), 0, c, 0, initVector.getBytes("UTF-8").length);
		 System.arraycopy(encrypted, 0, c, initVector.getBytes("UTF-8").length, encrypted.length);
		 newPassword = Base64.encodeBase64String(c);
		 
		 log.debug("Listening User Pre Persist :", newPassword);
		    
		} catch (Exception e) {
            e.printStackTrace();
            log.debug("Listening User Pre Persist :", e.getMessage());
        }
		
		user.setPassword("plaintext:"+user.getPassword());
	}
	
	public byte[] getRandomKey() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[8];
		random.nextBytes(bytes);
		return bytes;
	}

}
