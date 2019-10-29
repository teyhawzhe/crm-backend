package com.crm.service.role.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.crm.model.entity.TbRole;
import com.crm.repository.RoleRepository;
import com.crm.restController.role.RoleForm;
import com.crm.service.role.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository; 
	
	@Override
	public void save(RoleForm roleForm) throws Exception{
		// TODO Auto-generated method stub
		try {
			TbRole tbRole = new TbRole();
			tbRole.setDef(roleForm.getDef());
			tbRole.setRole(roleForm.getRole());
			roleRepository.save(tbRole);
		}catch(Exception ex) {
			log.debug("Exception "+ex);
			throw new Exception("新增失敗!");
		}
	}

	@Override
	public List<TbRole> getAllList() {
		// TODO Auto-generated method stub
		return roleRepository.findAll(Sort.by("id"));
	}

}
