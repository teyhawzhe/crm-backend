package com.crm.jwt;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDecisionManager implements AccessDecisionManager {
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		if (CollectionUtils.isEmpty(configAttributes)) {
			throw new AccessDeniedException("DISALLOWED_API");
		}
		Iterator<ConfigAttribute> ite = configAttributes.iterator();

		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String role = ca.getAttribute();
			if (role.equals("ANONYMOUS")) {
				role = "ROLE_" + ca.getAttribute();
			}
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (ga.getAuthority().equals(role)) {
					return;
				}
			}
		}
		throw new AccessDeniedException("DISALLOWED_API");

	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
