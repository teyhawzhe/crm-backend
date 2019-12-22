package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9107657645941645092L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "標題不能為空!")
	private String title;
	
	@NotBlank(message = "內容不能為空!")
	private String bulletin;
	
	private String creater;
	
	private String createDate;
	
}
