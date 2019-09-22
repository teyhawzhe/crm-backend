package com.crm.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crm.model.entity.Roles;
import com.crm.model.entity.Users;
import com.crm.repository.RolesRepository;
import com.crm.repository.UsersRepository;
import com.crm.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Users users = usersRepository.getOne(username);
		
		if(null!=users) {
			return new User(users.getUsername(), users.getPassword(),getRoles(username));
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
	}
	
	public Collection<GrantedAuthority> getRoles(String username){
		List<Roles> roles = rolesRepository.getAllRoleByUsername(username);
		
		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
		
		for(Roles index : roles) {
			grantedAuthority.add(new SimpleGrantedAuthority(index.getId().getRole()));
		}
		
		return grantedAuthority;
	}
}
