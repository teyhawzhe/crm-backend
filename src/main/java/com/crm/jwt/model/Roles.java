package com.crm.jwt.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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
public class Roles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8813057633729661917L;
	
	@EmbeddedId
	private RolesPk id;
}
