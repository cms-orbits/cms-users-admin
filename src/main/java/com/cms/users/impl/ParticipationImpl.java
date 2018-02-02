package com.cms.users.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cms.users.entity.Participation;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.ParticipationInt;
import com.cms.users.repo.ParticipationRepository;

@Component
public class ParticipationImpl implements ParticipationInt {
	public static org.slf4j.Logger log = LoggerFactory.getLogger(Participation.class);
	
	@Autowired
	private ParticipationRepository repo;
	
	@Override
	public Iterable<Participation> getParticipations() {
		// TODO Auto-generated method stub
		//log.debug("[{}]", user);
		return repo.findAll();
	}

	@Override
	public ResponseEntity<Participation> doCreate(Participation participation, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		Participation e = repo.save(participation);
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}
}