package com.crm.jwt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3657397431920715565L;

	@Id
	@Column(name = "USERNAME" , length = 200 , unique = true , nullable = false)
	private String username;
	
	@Column(name = "PASSWORD" , length = 200 , nullable = false)
	private String password;
	
	private boolean enable;
}
