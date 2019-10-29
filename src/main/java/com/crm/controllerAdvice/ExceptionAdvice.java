package com.crm.controllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crm.common.HttpStatus;
import com.crm.common.Result;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice{

	@ExceptionHandler(value = IllegalArgumentException.class)
	public Result<String> errorHandle(IllegalArgumentException ex){
		log.info("ex = " + ex.getLocalizedMessage());
		log.info("ex = " + ex.getMessage());
		ex.printStackTrace();
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	@ExceptionHandler(value = Exception.class)
	public Result<String> errorHandle(Exception ex){
		log.info("ex = " + ex.getLocalizedMessage());
		log.info("ex = " + ex.getMessage());
		ex.printStackTrace();
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	
	@ExceptionHandler(value = BadCredentialsException.class)
	public Result<String> errorHandle(BadCredentialsException ex){
		log.info("ex = " + ex.getLocalizedMessage());
		log.info("ex = " + ex.getMessage());
		ex.printStackTrace();
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	
	@ExceptionHandler(value = DisabledException.class)
	public Result<String> errorHandle(DisabledException ex){
		log.info("ex = " + ex.getLocalizedMessage());
		log.info("ex = " + ex.getMessage());
		ex.printStackTrace();
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public Result<String> errorHandle(AccessDeniedException ex){
		log.info("ex = " + ex.getLocalizedMessage());
		log.info("ex = " + ex.getMessage());
		ex.printStackTrace();
		return new Result<String>(HttpStatus.exception,"權限不足!");	
	}
	
}
