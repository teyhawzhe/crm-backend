package com.crm.service.apiPermission;

import java.util.List;

import com.crm.model.entity.ApiPermission;
import com.crm.restController.apiPermission.ApiPermissionForm;

public interface ApiPermissionService {
	
	public List<ApiPermission> findAll();

	public void save(ApiPermissionForm form) throws Exception;
	
	public List<String> findAllByRole(ApiPermissionForm form);
}
