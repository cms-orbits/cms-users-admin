package com.joelgtsantos.cmsusers.inte;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.joelgtsantos.cmsusers.entity.User;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;

@RestController
@RequestMapping(value = "/api/v1", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public interface CmsServicesInt {
	
	/*
	 * Login
	 * */
	@Transactional(readOnly = false)
	@RequestMapping(value = {"","/login"},method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError;
	
	/*
	 * Register and login with social accounts
	 * */
	@RequestMapping(value = "/register", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> socialRegister( 
			@RequestParam Map<String, String> queryParameters,
			@RequestParam MultiValueMap<String, String> multiMap,
			HttpServletRequest request,
			HttpServletResponse response) 
	            		throws ExceptionInternalError;
	
	@RequestMapping(value = "/redirect", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView redirect(
			@RequestParam Map<String, String> queryParameters,
			@RequestParam MultiValueMap<String, String> multiMap,
			HttpServletResponse response) 
	            		throws ExceptionInternalError;
	
}
