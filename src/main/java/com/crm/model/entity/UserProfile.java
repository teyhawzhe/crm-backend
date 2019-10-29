package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9150442751399532994L;

	private String image;
	
	private String name;
	@Id
	private String username;
	
	private String createDate;
	
	private String creater;
}
