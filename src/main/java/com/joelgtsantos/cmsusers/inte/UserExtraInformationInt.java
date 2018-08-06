package com.joelgtsantos.cmsusers.inte;

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

import com.joelgtsantos.cmsusers.entity.ResponseTransfer;
import com.joelgtsantos.cmsusers.entity.UserExtraInformation;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;

@RestController
@RequestMapping(value = "/api/v1/user/extra", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public interface UserExtraInformationInt {
	
	/**
	 *
	 * @param ID
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	@RequestMapping(value = {"",""},method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  Iterable<UserExtraInformation> getUsers();
	
	
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
	ResponseEntity<UserExtraInformation> doCreate(@RequestBody UserExtraInformation userextra, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError;
	 

	/**
	 *
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	@Transactional(readOnly = false)
	@RequestMapping(value = { "", "/file" },method= RequestMethod.PUT)
	ResponseEntity<ResponseTransfer> doCV(@RequestBody ResponseTransfer file, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError;
}
