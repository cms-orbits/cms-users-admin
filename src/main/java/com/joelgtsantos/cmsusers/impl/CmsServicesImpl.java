package com.joelgtsantos.cmsusers.impl;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.joelgtsantos.cmsusers.entity.CookieGenerator;
import com.joelgtsantos.cmsusers.entity.Email;
import com.joelgtsantos.cmsusers.entity.EventPublisher;
import com.joelgtsantos.cmsusers.entity.Participation;
import com.joelgtsantos.cmsusers.entity.User;
import com.joelgtsantos.cmsusers.entity.UserExtraInformation;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;
import com.joelgtsantos.cmsusers.inte.CmsServicesInt;
import com.joelgtsantos.cmsusers.repo.ParticipationRepository;
import com.joelgtsantos.cmsusers.repo.UserExtraInformationRepository;
import com.joelgtsantos.cmsusers.repo.UserRepository;
import com.joelgtsantos.cmsusers.security.ApplicationProperties;
import com.joelgtsantos.cmsusers.service.utilities.Crypto;

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
	
	@Autowired
	private UserExtraInformationRepository repoExtraInfo;
	
	@Autowired
	EventPublisher eventPublisherService;
	
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
			Principal principal,
			HttpServletRequest request,
			HttpServletResponse response) throws ExceptionInternalError {

		Map details = (Map)((OAuth2Authentication)principal).getUserAuthentication().getDetails();
        String userEmail = (String)details.get("email");
        String userNames = (String)details.get("name");
        String userName = userEmail.replaceAll("[^A-Za-z0-9]", "");
		
        User userDb = repoUser.findByEmailEquals(userEmail);

		//Verify if user exist
		if (userDb == null) {
			User user = new User();
			user.setFirstName(userNames);
			user.setLastName("");
		    user.setUsername(userName);
		    user.setEmail(userEmail);
			user.setTimezone("");
			user.setPreferredLanguages("");
		
			//Default password
			user.setPassword("uZd3dj0$cpeuw12pqz");
			userDb = repoUser.save(user);
			
			//Due to it's first time login into the system a new participation is created
			Participation participation = new Participation();
			participation.setContestId(repoParticipation.findOne());
			participation.setUserId(user.getId());
			repoParticipation.save(participation);
			
			//Due to it's first time login into the system a new profile is created
			UserExtraInformation profile = new UserExtraInformation();
			profile.setId(user.getId());
			profile.setBirthdate(new Date());
			repoExtraInfo.save(profile);
			
			//Send email after register process
			Email email = new Email(user.getFirstName() + " " + user.getLastName(), 
								"cms@gmail.com",
								user.getEmail(),
								"Signup CMS - Completed",
								"Welcome to CMS Coding Challenge"
								);
			
			eventPublisherService.sendMessageRegister(email);
			
			userDb.setRedirect(false);
		}
		
		
		//Set cookie to a request 
		Cookie cookie = cookieGenerator.generateCookie(userDb.getUsername(), userDb.getPassword());
		
		cookie.setDomain(appProperties.getCmsDomain());
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return new ResponseEntity<>(userDb, HttpStatus.CREATED);		
	}

	@Override
	public ModelAndView redirect(
			@RequestParam Map<String, String> queryParameters,
			@RequestParam MultiValueMap<String, String> multiMap,
			HttpServletResponse response)
			throws ExceptionInternalError {
		
		String port = appProperties.getCmsPort() != null && appProperties.getCmsPort() != "" ? ":" + appProperties.getCmsPort() : "";
		User userDb = repoUser.findByEmailEquals(multiMap.get("email").get(0));

		Cookie cookie = cookieGenerator.generateCookie(userDb.getUsername(), userDb.getPassword());
		
		cookie.setDomain(appProperties.getCmsDomain());
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return new ModelAndView("redirect:http://" + appProperties.getCmsDomain() + port);		
	}
	
	
}
