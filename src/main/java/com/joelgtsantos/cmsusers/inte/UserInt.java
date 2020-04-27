package com.joelgtsantos.cmsusers.inte;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joelgtsantos.cmsusers.entity.InfoUsersRequestDTO;
import com.joelgtsantos.cmsusers.entity.User;
import com.joelgtsantos.cmsusers.entity.UserDTO;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;

@RestController
@RequestMapping(value = "/v1/users", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public interface UserInt {

	/**
	 *
	 * @param ID
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	@RequestMapping(value = {"","/"},method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  Iterable<User> getUsers(HttpServletRequest request);


	/**
	 *
	 * @param ID
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	@RequestMapping(value = "/{id}" ,method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User getUser(@PathVariable("id") long id,
									 HttpServletRequest request);

	/**
	 *
	 * @param InfoUsersRequestDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorInternoException
	 */
	@Transactional(readOnly = false)
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST)
	@ResponseBody
	public Set<UserDTO> getInfoUsers(@RequestBody InfoUsersRequestDTO infoUsersRequestDTO,
									  HttpServletRequest request,
									  HttpServletResponse response)
	throws ExceptionInternalError;
}
