package com.cms.users.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cms.service.utilities.Crypto;
import com.cms.users.entity.Participation;
import com.cms.users.entity.User;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.CmsServicesInt;
import com.cms.users.repo.ParticipationRepository;
import com.cms.users.repo.UserRepository;

@Component
public class CmsServicesImpl implements CmsServicesInt {
	@Autowired
	private UserRepository repoUser;
	private ParticipationRepository repoPartcipation;
	
	
	@Override
	public ResponseEntity<User> login (User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {

		//Verify if user exists		
		User us = repoUser.findByEmailEquals(user.getEmail());
		
		//Validate password
		
		System.out.println(Crypto.encryptPlain(user.getPassword()));
		System.out.println(us.getPassword());
		
		if(Crypto.isValidPassword(Crypto.encryptPlain(user.getPassword()), us)!= true)
			us = null;
		
		us.setPassword(null);
		
		return new ResponseEntity<>(us, HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<User> socialRegistration(User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		User userDb = repoUser.findByEmailEquals(user.getEmail());
		//Verify if user exists

		if (userDb == null) {
			//Default password
			user.setPassword("uZd3dj0$cpeuw12pqz");
			userDb = repoUser.save(user);
			//Participation participation = new Participation();
			/*  participation.ip = null;
            participation.startingTime = 0;
            participation.delayTime = "00:00:00";
            participation.extraTime = "00:00:00";
            participation.password = "";
            participation.hidden = false;
            participation.unrestricted = false;
            participation.contestId = 2;
            participation.userId = data.id;
            participation.teamId = null;
			 * */
			
			//participation.getStartingTime();
			//Participation e = repoPartcipation.save(participation);
		}
		
		//userDb.setPassword(null);
		
		return new ResponseEntity<>(userDb, HttpStatus.CREATED);
	}
	
	public boolean userExists() {
		return true;
	}

}
