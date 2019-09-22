package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.model.entity.Roles;
import com.crm.repository.RolesRepository;
import com.crm.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RolesRepository rolesRepository;
	
	@Override
	public List<Roles> getRoles(String username) {
		// TODO Auto-generated method stub
		return rolesRepository.getAllRoleByUsername(username);
	}

}
