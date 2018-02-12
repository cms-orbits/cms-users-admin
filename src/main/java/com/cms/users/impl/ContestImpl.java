package com.cms.users.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cms.users.entity.Contest;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.ContestInt;
import com.cms.users.repo.ContestRepository;

@Component
public class ContestImpl implements ContestInt {
	public static org.slf4j.Logger log = LoggerFactory.getLogger(Contest.class);
	
	@Autowired
	private ContestRepository repo;
	
	@Override
	public Iterable<Contest> getContests() {
		// TODO Auto-generated method stub
		//log.debug("[{}]", user);
		return repo.findAll();
	}

	@Override
	public ResponseEntity<Contest> doCreate(Contest contest, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		Contest e = repo.save(contest);
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}
}