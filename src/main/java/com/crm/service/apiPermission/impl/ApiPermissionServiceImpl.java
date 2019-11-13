package com.crm.service.apiPermission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.ApiPermission;
import com.crm.model.entity.ApiPermissionPk;
import com.crm.repository.ApiPermissionRepository;
import com.crm.restController.apiPermission.ApiPermissionForm;
import com.crm.restController.role.RoleForm;
import com.crm.service.apiPermission.ApiPermissionService;

@Service
public class ApiPermissionServiceImpl implements ApiPermissionService {

	@Autowired
	private ApiPermissionRepository apiPermissionRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<ApiPermission> findAll() {
		// TODO Auto-generated method stub
		return apiPermissionRepository.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(ApiPermissionForm form) throws Exception {
		// TODO Auto-generated method stub
		try {
			apiPermissionRepository.delete(form.getRole());
		for(String api:form.getApis()) {
			ApiPermissionPk id = new ApiPermissionPk();
			id.setRole(form.getRole());
			id.setApi(api);
			ApiPermission apiPermission = new ApiPermission();
			apiPermission.setId(id);
			apiPermissionRepository.save(apiPermission);
		}
		}catch(Exception ex) {
			throw new Exception("新增失敗!",ex);
		}
	}

	@Override
	public List<String> findAllByRole(ApiPermissionForm form) {
		// TODO Auto-generated method stub
		return apiPermissionRepository.findAllByRole(form.getRole());
	}

}
