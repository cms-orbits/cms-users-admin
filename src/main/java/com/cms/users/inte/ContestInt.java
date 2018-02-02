package com.cms.users.inte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cms.users.entity.Contest;
import com.cms.users.exception.ExceptionInternalError;

@RestController
@RequestMapping(value = "/api/contest", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public interface ContestInt {
	
	/**
	 *
	 * @param ID
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	
	@RequestMapping(value = {"","/"},method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  Iterable<Contest> getContests();
	
	
	/**
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	@Transactional(readOnly = false)
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST)
	ResponseEntity<Contest> doCreate(@RequestBody Contest participation, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError;


}
