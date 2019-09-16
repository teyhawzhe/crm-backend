package com.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.Users;
import com.crm.repository.hibernate.UsersHRepository;
import com.crm.service.UsersService;

@Service
public class UserServiceImpl implements UsersService {

	@Autowired
	private UsersHRepository usersHRepository;
	
	@Override
	public Users getPassword(String username) {
		// TODO Auto-generated method stub
		return usersHRepository.getOne(username);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Users save(Users users) {
		// TODO Auto-generated method stub
		return usersHRepository.save(users);
	}

}
