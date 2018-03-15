package com.cms.users.inte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cms.users.entity.User;
import com.cms.users.exception.ExceptionInternalError;

@RestController
@RequestMapping(value = "/api/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public interface CmsServicesInt {
	
	/*
	 * Login
	 * */
	@Transactional(readOnly = false)
	@RequestMapping(value = {"","/login"},method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError;
	
	/*
	 * Registration and login with social accounts
	 * */
	@Transactional(readOnly = false)
	@RequestMapping(value = {"","/register"},method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> socialRegistration(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError;
	
	

}
