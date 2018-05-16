package com.cms.users.impl;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cms.service.utilities.Crypto;
import com.cms.users.ApplicationProperties;
import com.cms.users.entity.CookieGenerator;
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
	
	@Autowired
	CookieGenerator cookieGenerator;
	
	@Autowired
	private ApplicationProperties appProperties;
	
	@Autowired
	private ParticipationRepository repoParticipation;
	
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
	public ResponseEntity<User> socialRegister(
			Map<String, String> queryParameters,
			MultiValueMap<String, String> multiMap,
			HttpServletRequest request,
			HttpServletResponse response) throws ExceptionInternalError {
		
        User userDb = repoUser.findByEmailEquals(multiMap.get("email").get(0));

		//Verify if user exist
		if (userDb == null) {
			User user = new User();
			user.setFirstName(multiMap.get("firstName").get(0));
			user.setLastName(multiMap.get("lastName").get(0));
		    user.setUserame(multiMap.get("username").get(0));
		    user.setEmail(multiMap.get("email").get(0));
			user.setTimezone("");
			user.setPreferredLanguages("");
			
			//Default password
			user.setPassword("uZd3dj0$cpeuw12pqz");
			userDb = repoUser.save(user);
			
			//participation.contestId = 2;
            //participation.userId = data.id;
			Participation participation = new Participation();
			participation.setContestId(new Long(2));
			participation.setUserId(new Long(user.getId()));
			
			if (repoParticipation.exist(participation)==null)
				participation = repoParticipation.save(participation);
			
			userDb.setRedirect(false);
		}
		
		return new ResponseEntity<>(userDb, HttpStatus.CREATED);		
	}

	@Override
	public ModelAndView redirect(
			@RequestParam Map<String, String> queryParameters,
			@RequestParam MultiValueMap<String, String> multiMap,
			HttpServletResponse response)
			throws ExceptionInternalError {
		
		User userDb = repoUser.findByEmailEquals(multiMap.get("email").get(0));

		Cookie cookie = cookieGenerator.generateCookie(userDb.getUsername(), userDb.getPassword());
		//cookie.setMaxAge(60 * 24 * 3600);
		cookie.setDomain(appProperties.getUrlCmsDomain());
		cookie.setPath("/");
		response.addCookie(cookie);
		return new ModelAndView("redirect:http://" + appProperties.getUrlCmsDomain() + ":" + appProperties.getCmsPort());		
	}
	
	
}
