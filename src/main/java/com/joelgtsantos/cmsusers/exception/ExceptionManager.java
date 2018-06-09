package com.joelgtsantos.cmsusers.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionManager {

	public static org.slf4j.Logger log = LoggerFactory.getLogger(ExceptionManager.class);

	@ResponseBody
	@ExceptionHandler(ExceptionInternalError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	Map<String, String> entidadInternalServerErrorExceptionHandler(ExceptionInternalError ex) {
		Map<String, String> res = new HashMap<>();
		res.put("title", "Internal server error");
		res.put("message", "Unexpected internal error 500");
		res.put("error", ex.getMessage());
		return res;
	}

}
