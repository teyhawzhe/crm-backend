package com.crm.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserProfileUtils {
	
	public static String getUsername() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String currentPrincipalName = null;
		if(null!=authentication) {
			currentPrincipalName = authentication.getName();
		}
	
		return currentPrincipalName;
	}
	
	public static List<String> getRoles(){ 
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	
		List<String> roles = new ArrayList<String>();
		for(SimpleGrantedAuthority index : authorities) {
			roles.add(index.getAuthority());
		}
		
		return roles;
	}
	
}
