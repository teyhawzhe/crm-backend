package com.crm.restController.frontendPermission;

import java.io.Serializable;
import java.util.List;

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

	private String path;
	
	private List<String> role;
	
}
