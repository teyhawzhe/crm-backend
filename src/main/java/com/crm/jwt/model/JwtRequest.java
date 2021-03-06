package com.crm.jwt.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 917335911173125898L;

	private String username;
	
	private String password;
}
