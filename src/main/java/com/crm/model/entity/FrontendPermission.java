package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendPermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653739082293916327L;

	@EmbeddedId
	private FrontendPermissionPk id;
	
}
