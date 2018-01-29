package com.cms.users.ws.sec.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms.users.entity.sec.User;
import com.cms.users.ws.sec.inte.UserInt;
import com.cms.users.ws.sec.repo.UserRepository;

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
}