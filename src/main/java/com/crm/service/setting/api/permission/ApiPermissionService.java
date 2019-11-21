package com.crm.service.setting.api.permission;

import java.util.List;

import com.crm.model.entity.SettingApiPermission;
import com.crm.restController.setting.api.permission.Form;

public interface ApiPermissionService {
	
	public List<SettingApiPermission> findAll();

	public void save(Form form) throws Exception;
	
	public List<String> findAllByRole(Form form);
}
