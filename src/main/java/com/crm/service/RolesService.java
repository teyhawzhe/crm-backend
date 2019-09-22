package com.crm.service;

import java.util.List;

import com.crm.model.entity.Roles;

public interface RolesService {
	
	public List<Roles> getRoles(String username);
}
