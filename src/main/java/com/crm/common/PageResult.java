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
public class PageResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 196719912526387262L;

	public PageResult(String status,T data){
		this.status=status;
		this.data=data;
	}
	
	public PageResult(String status,T data,long total,int current){
		this.status=status;
		this.data=data;
		this.total=total;
		this.current=current;
	}
	
	private String status;
	
	private T data;
	
	private String message;
	
	private long total = 0;
	
	private long current = 0;
	
	private long pageSize = 0;
	
}
