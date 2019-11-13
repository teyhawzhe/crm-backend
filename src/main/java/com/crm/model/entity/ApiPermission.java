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
public class ApiPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4622366673197723918L;

	@EmbeddedId
	private ApiPermissionPk id;
	
}
