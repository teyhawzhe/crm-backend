package com.crm.jwt.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class JwtResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -6788750522946110854L;

	private final String data;
	
	private final int code;
}
