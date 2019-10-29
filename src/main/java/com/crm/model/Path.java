package com.crm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Path implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 506086516622421372L;

	private String path;
	
	private Meta meta;
	
	private String component;
	
	private Set<Path> children;
	
}
