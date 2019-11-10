package com.crm.service.frontendPermission;

import java.util.List;

import com.crm.restController.frontendPermission.FrontendPermissionInsertForm;

public interface FrontendPermissionService {
	
	public void save(FrontendPermissionInsertForm form) throws Exception;
	
	public List<String> query(String role);
	
}
