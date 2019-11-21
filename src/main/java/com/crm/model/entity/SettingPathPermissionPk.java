package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class SettingPathPermissionPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3934461164382053397L;

	private String path;
	
	private String role;
	
	private int tier;
}
