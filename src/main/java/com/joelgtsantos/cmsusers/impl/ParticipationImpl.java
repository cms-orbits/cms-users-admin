package com.joelgtsantos.cmsusers.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.joelgtsantos.cmsusers.entity.Participation;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;
import com.joelgtsantos.cmsusers.inte.ParticipationInt;
import com.joelgtsantos.cmsusers.repo.ParticipationRepository;

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
		
		if (repo.exist(participation)==null)
			participation = repo.save(participation);
		
		return new ResponseEntity<>(participation, HttpStatus.CREATED);
	}
}