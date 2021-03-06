package com.crm.restController.setting.api.permission;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Form implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4660184651715189456L;
	
	@NotBlank(message = "角色不能為空")
	private String role;
	
	private List<String> apis;
	
}
