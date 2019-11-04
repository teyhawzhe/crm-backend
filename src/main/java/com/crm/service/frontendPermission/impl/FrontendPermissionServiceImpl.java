package com.crm.service.frontendPermission.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.FrontendPermission;
import com.crm.model.entity.FrontendPermissionPk;
import com.crm.repository.FrontendPermissionRepository;
import com.crm.restController.frontendPermission.FrontendPermissionInsertForm;
import com.crm.service.frontendPermission.FrontendPermissionService;

@Service
public class FrontendPermissionServiceImpl implements FrontendPermissionService {

	@Autowired
	private FrontendPermissionRepository frontendPermissionRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(FrontendPermissionInsertForm form) throws Exception {
		// TODO Auto-generated method stub
		try {
			for(String role : form.getRole()) {
				FrontendPermissionPk pk = new FrontendPermissionPk();
				pk.setPath(form.getPath());
				pk.setRole(role);
				FrontendPermission frontendPermission =  new FrontendPermission(pk);
				frontendPermissionRepository.save(frontendPermission);
			}
		}catch(Exception ex) {
			throw new Exception("新增失敗!");
		}
	}
	
	
}
