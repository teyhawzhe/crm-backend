package com.crm.common;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageObject<T> implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 9182260439873772447L;

	List<T> content;

	private long total;

	private long pageSize;

}
