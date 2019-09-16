package com.crm.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crm.model.entity.Users;
import com.crm.repository.hibernate.UsersHRepository;
import com.crm.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersHRepository usersHRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		/*if ("admin".equals(username)) {
			return new User("admin", "$2a$10$eyQYIqFxZR7hs8uhaqpxFO9HsvwUbmpZP1Q9zuABgezA45EZosyUm",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}*/
		
		Users users = usersHRepository.getOne(username);
		
		if(null!=users) {
			return new User(users.getUsername(), users.getPassword(),new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
	}

}
