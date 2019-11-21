package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingApiPermissionPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384546627877493623L;

	private String api;
	
	private String role;
}
