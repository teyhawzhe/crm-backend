package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class LoginAttempt implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -2101352790184649502L;

	@Id
	private String username;
	
	private int attempt;
	
	private String allowDate;
	
}
