package com.crm.service.setting.path.permission;

import java.util.List;

import com.crm.restController.setting.path.permission.AddForm;

public interface FrontendPermissionService {
	
	public void save(AddForm form) throws Exception;
	
	public List<String> query(String role);
	
}
