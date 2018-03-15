package com.cms.users.impl;

import java.io.FileOutputStream;
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
import com.cms.users.entity.ResponseTransfer;
import com.cms.users.entity.UserExtraInformation;
import com.cms.users.exception.ExceptionInternalError;
import com.cms.users.inte.UserExtraInformationInt;
import com.cms.users.repo.UserExtraInformationRepository;

@Component
public class UserExtraInformationImpl implements UserExtraInformationInt {
	/**
	 *
	 */
	public static org.slf4j.Logger log = LoggerFactory.getLogger(UserExtraInformationImpl.class);
	@Autowired
	private UserExtraInformationRepository repo;
	private static String UPLOADED_FOLDER = "C:\\temp\\";
	
	@Override
	public Iterable<UserExtraInformation> getUsers() {
		// TODO Auto-generated method stub
		//log.debug("[{}]", user);
		return repo.findAll();
	}

	@Override
	public ResponseEntity<UserExtraInformation> doCreate(UserExtraInformation userextra, HttpServletRequest request, HttpServletResponse response)
			throws ExceptionInternalError {
		UserExtraInformation e = repo.save(userextra);
		
		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseTransfer> doCV(ResponseTransfer file, HttpServletRequest request, HttpServletResponse response)
		throws ExceptionInternalError {
		System.out.print(file.getFilename());
		FileOutputStream outFile = null;
		
        try {
            // Get the file and save it locally
            //Path path = Paths.get(UPLOADED_FOLDER + file.getFilename());
            outFile = new FileOutputStream(UPLOADED_FOLDER + file.getFilename());
            outFile.write(file.getFile());
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
				outFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		return new ResponseEntity<>(file, HttpStatus.CREATED);
	}
}