package com.crm.service;

import com.crm.model.entity.Users;

public interface UsersService {
	
	public Users getPassword(String username);
	
	public Users save(Users users);
}
