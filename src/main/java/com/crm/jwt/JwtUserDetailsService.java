package com.crm.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crm.jwt.model.Roles;
import com.crm.jwt.model.Users;
import com.crm.jwt.repository.RolesRepository;
import com.crm.jwt.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Users users = usersRepository.getOne(username);
		
		if (null!=users) {
			return new User(users.getUsername(),users.getPassword(),users.isEnable(),true,true,true,getRoles(username));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	public List<GrantedAuthority> getRoles(String username){
		List<Roles> roles = rolesRepository.findAllByIdUsername(username);
		
		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
		log.info(" grantedAuthority " + roles.size());
		for(Roles index : roles) {
			log.info(" roles " + index.toString());
			grantedAuthority.add(new SimpleGrantedAuthority(index.getId().getRole()));
		}
		
		return grantedAuthority;
	}
	
}
