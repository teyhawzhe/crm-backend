package com.crm.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 196719912526387262L;

	public Result(String status,String message){
		this.status=status;
		this.message=message;
	}
	
	private String status;
	
	private String message;
	
	private T data;
	
}
