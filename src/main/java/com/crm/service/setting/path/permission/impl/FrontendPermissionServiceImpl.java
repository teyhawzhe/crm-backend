package com.crm.service.setting.path.permission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.SettingPathPermission;
import com.crm.model.entity.SettingPathPermissionPk;
import com.crm.repository.SettingPathPermissionRepository;
import com.crm.restController.setting.path.permission.AddForm;
import com.crm.service.setting.path.permission.FrontendPermissionService;

@Service
public class FrontendPermissionServiceImpl implements FrontendPermissionService {

	@Autowired
	private SettingPathPermissionRepository frontendPermissionRepository;

	/**
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(AddForm form) throws Exception {
		// TODO Auto-generated method stub
		try {
			frontendPermissionRepository.deleteByRole(form.getRole());
			
			for(String path : form.getPaths()) {
				SettingPathPermissionPk pk = new SettingPathPermissionPk();
				pk.setRole(form.getRole());
				String[] arr=path.split("&");
				pk.setPath(arr[0]);
				pk.setTier(Integer.valueOf(arr[1]));
				SettingPathPermission frontendPermission =  new SettingPathPermission(pk);
				frontendPermissionRepository.save(frontendPermission);
			}
		}catch(Exception ex) {
			throw new Exception("新增失敗!",ex);
		}
	}
	@Transactional(readOnly = true)
	@Override
	public List<String> query(String role) {
		// TODO Auto-generated method stub
		return frontendPermissionRepository.query(role);
	}
	
	
}
