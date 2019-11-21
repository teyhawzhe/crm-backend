package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Embeddable
@Data
public class LoginHistoryPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2525872123940834624L;

	private String username;
	
	private String loginTime;
	
}
