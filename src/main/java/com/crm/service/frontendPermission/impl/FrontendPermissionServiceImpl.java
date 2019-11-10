package com.crm.service.frontendPermission.impl;

import java.util.List;

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

	/**
	 *
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(FrontendPermissionInsertForm form) throws Exception {
		// TODO Auto-generated method stub
		try {
			frontendPermissionRepository.deleteByRole(form.getRole());
			
			for(String path : form.getPaths()) {
				FrontendPermissionPk pk = new FrontendPermissionPk();
				pk.setRole(form.getRole());
				String[] arr=path.split("&");
				pk.setPath(arr[0]);
				pk.setTier(Integer.valueOf(arr[1]));
				FrontendPermission frontendPermission =  new FrontendPermission(pk);
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
