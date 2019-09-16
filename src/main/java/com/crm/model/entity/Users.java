package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3003026879822628805L;
	
	@Id
	@Column(name = "USERNAME" ,unique = true , length = 20)
	private String username;
	
	@Column(name = "PASSWORD" ,length = 200)
	private String password;
	
	@Column(name = "ENABLE")
	private boolean enable;
}
