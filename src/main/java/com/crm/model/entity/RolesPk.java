package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RolesPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3210003413825845305L;
	
	private String username;
	
	private String role;
	
}
