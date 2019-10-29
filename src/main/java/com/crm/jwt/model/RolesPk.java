package com.crm.jwt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RolesPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3210003413825845305L;

	@Column(name = "USERNAME" , length = 200 , unique = true , nullable = false)
	private String username;
	
	@Column(name = "ROLE" , length = 200 , nullable = false)
	private String role;
}
