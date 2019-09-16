package com.crm.jwt.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserInfo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8305053231990317487L;
	
	private String name;
	
	private List<String> roles;
	
	private String avatar;
	
	private String introduction;
	
}
