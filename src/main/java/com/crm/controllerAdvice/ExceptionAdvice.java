package com.crm.controllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.exception.SqlConditionItemException;

@RestControllerAdvice
public class ExceptionAdvice{

	@ExceptionHandler(value = SqlConditionItemException.class)
	public Result<String> errorHandle(SqlConditionItemException ex){
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	@ExceptionHandler(value = IllegalArgumentException.class)
	public Result<String> errorHandle(IllegalArgumentException ex){
		return new Result<String>(HttpStatus.exception,ex.getLocalizedMessage());	
	}
	@ExceptionHandler(value = Exception.class)
	public Result<String> errorHandle(Exception ex){
		return new Result<String>(HttpStatus.exception,ex.getMessage());	
	}
}
