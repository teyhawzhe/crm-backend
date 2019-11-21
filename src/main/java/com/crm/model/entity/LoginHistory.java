package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class LoginHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2525872123940834624L;

	@EmbeddedId
	private LoginHistoryPk id ;
	
	private boolean success;
	
	private String reason;
}
