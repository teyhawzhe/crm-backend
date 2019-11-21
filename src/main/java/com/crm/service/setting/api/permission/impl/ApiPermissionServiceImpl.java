package com.crm.service.setting.api.permission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.SettingApiPermission;
import com.crm.model.entity.SettingApiPermissionPk;
import com.crm.repository.SettingApiPermissionRepository;
import com.crm.restController.setting.api.permission.Form;
import com.crm.restController.setting.role.AddForm;
import com.crm.service.setting.api.permission.ApiPermissionService;

@Service
public class ApiPermissionServiceImpl implements ApiPermissionService {

	@Autowired
	private SettingApiPermissionRepository apiPermissionRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<SettingApiPermission> findAll() {
		// TODO Auto-generated method stub
		return apiPermissionRepository.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(Form form) throws Exception {
		// TODO Auto-generated method stub
		try {
			apiPermissionRepository.delete(form.getRole());
		for(String api:form.getApis()) {
			SettingApiPermissionPk id = new SettingApiPermissionPk();
			id.setRole(form.getRole());
			id.setApi(api);
			SettingApiPermission apiPermission = new SettingApiPermission();
			apiPermission.setId(id);
			apiPermissionRepository.save(apiPermission);
		}
		}catch(Exception ex) {
			throw new Exception("新增失敗!",ex);
		}
	}

	@Override
	public List<String> findAllByRole(Form form) {
		// TODO Auto-generated method stub
		return apiPermissionRepository.findAllByRole(form.getRole());
	}

}
