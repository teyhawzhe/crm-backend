package com.crm.restController.frontendPermission;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendPermissionInsertForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5499045579465771308L;

	@NotBlank(message = "請選擇角色!")
	private String role;
	
	private List<String> paths;
	
	private List<String> excludePaths;
}
