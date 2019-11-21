package com.crm.controllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crm.common.AttemptException;
import com.crm.common.HttpStatus;
import com.crm.common.Result;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice{

	@ExceptionHandler(value = IllegalArgumentException.class)
	public Result<String> errorHandle(IllegalArgumentException ex){
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	@ExceptionHandler(value = Exception.class)
	public Result<String> errorHandle(Exception ex){
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	
	@ExceptionHandler(value = BadCredentialsException.class)
	public Result<String> errorHandle(BadCredentialsException ex){
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	
	@ExceptionHandler(value = DisabledException.class)
	public Result<String> errorHandle(DisabledException ex){
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public Result<String> errorHandle(AccessDeniedException ex){
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new Result<String>(HttpStatus.exception,"權限不足!");	
	}
	@ExceptionHandler(value = AttemptException.class)
	public Result<String> errorHandle(AttemptException ex) {
		StringWriter stack = new StringWriter();
		ex.printStackTrace(new PrintWriter(stack));
		log.error(stack.toString());
		return new Result<String>(HttpStatus.exception, "LOGIN_ATTEMPT",ex.getLocalizedMessage());
	}
	
}
