package com.cms.users.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.cms.service.utilities.Crypto;
import com.cms.users.ApplicationProperties;
import com.cms.users.entity.CookieGenerator;
import com.cms.users.entity.User;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.CmsServicesInt;
import com.cms.users.repo.UserRepository;

@Component
public class CmsServicesImpl implements CmsServicesInt {
	@Autowired
	private UserRepository repoUser;
	
	@Autowired
	CookieGenerator cookieGenerator;
	
	@Autowired
	private ApplicationProperties appProperties;
	
	@Override
	public ResponseEntity<User> login (User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {

		//Verify if user exists		
		User us = repoUser.findByEmailEquals(user.getEmail());
		
		//Validate password
		if(Crypto.isValidPassword(Crypto.encryptPlain(user.getPassword()), us)!= true)
			us = null;

		return new ResponseEntity<>(us, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<User> socialRegistration(User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		User userDb = repoUser.findByEmailEquals(user.getEmail());

		//Verify if user exist
		if (userDb == null) {
			//Default password
			user.setPassword("uZd3dj0$cpeuw12pqz");
			userDb = repoUser.save(user);
		}
		
		cookieGenerator.generateCookie(user.getUsername(), user.getPassword());
		
		//return new ModelAndView("redirect:" + appProperties.getUrlRedirect());
		userDb.setRedirect(appProperties.getUrlRedirect()); 
		return new ResponseEntity<>(userDb, HttpStatus.CREATED);
	}
}
