package com.joelgtsantos.cmsusers.impl;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.joelgtsantos.cmsusers.entity.InfoUsersRequestDTO;
import com.joelgtsantos.cmsusers.entity.User;
import com.joelgtsantos.cmsusers.entity.UserDTO;
import com.joelgtsantos.cmsusers.exception.ExceptionInternalError;
import com.joelgtsantos.cmsusers.inte.UserInt;
import com.joelgtsantos.cmsusers.repo.UserRepository;

@Component
public class UserImpl implements UserInt {
	/**
	 *
	 */
	public static org.slf4j.Logger log = LoggerFactory.getLogger(UserImpl.class);
	@Autowired
	private UserRepository repo;
	
	@Override
	public Iterable<User> getUsers(HttpServletRequest request) {
		return null;
	}

	@Override
	public Set<UserDTO> getInfoUsers(@RequestBody InfoUsersRequestDTO infoUsersRequestDTO, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		Set<UserDTO> listUsers = new HashSet<>();
		
		//Searching for each ID
		for(Long id: infoUsersRequestDTO.getUsers()){
			UserDTO userDTO = repo.findUserById(id);
			if (userDTO != null) {
				listUsers.add(userDTO);
			}
		}
		
		return listUsers;
	}

	/* (non-Javadoc)
	 * @see com.joelgtsantos.cmsusers.inte.UserInt#getUser(long, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public User getUser(long id, HttpServletRequest request) {
		return repo.findById(id);
	}
}