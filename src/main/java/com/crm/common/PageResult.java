package com.crm.common;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JacksonXmlRootElement
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
	
	private String status;
	
	private T data;
	
	private String message;
	
	private long current;
	
	private long total;
	
	private long pageSize;
	
}
