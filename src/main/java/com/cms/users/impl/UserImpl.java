package com.cms.users.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cms.users.entity.User;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.UserInt;
import com.cms.users.repo.UserRepository;

@Component
public class UserImpl implements UserInt {
	/**
	 *
	 */
	public static org.slf4j.Logger log = LoggerFactory.getLogger(UserImpl.class);
	@Autowired
	private UserRepository repo;
	
	@Override
	public Iterable<User> getUsers() {
		// TODO Auto-generated method stub
		//log.debug("[{}]", user);
		return repo.findAll();
	}

	@Override
	public ResponseEntity<User> doCreate(User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		User e = repo.save(user);
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}
}