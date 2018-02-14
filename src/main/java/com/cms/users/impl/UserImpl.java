package com.cms.users.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cms.users.entity.ResponseTransfer;
import com.cms.users.entity.User;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.UserInt;
import com.cms.users.repo.UserRepository;

@Component
public class UserImpl implements UserInt {
	/**
	 *
	 */
	public static org.slf4j.Logger log = LoggerFactory.getLogger(UserImpl.class);
	@Autowired
	private UserRepository repo;
	private static String UPLOADED_FOLDER = "C:\\temp\\";
	
	@Override
	public Iterable<User> getUsers() {
		// TODO Auto-generated method stub
		//log.debug("[{}]", user);
		return repo.findAll();
	}

	@Override
	public ResponseEntity<User> doCreate(User user, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		User e = repo.save(user);
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseTransfer> doCV(ResponseTransfer file, HttpServletRequest request, HttpServletResponse response)
		throws ExceptionInternalError {
		System.out.print(file.getFilename());
			
        try {
            // Get the file and save it locally
            Path path = Paths.get(UPLOADED_FOLDER + file.getFilename());
            Files.write(path, file.getFile());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return new ResponseEntity<>(file, HttpStatus.CREATED);
	}
}