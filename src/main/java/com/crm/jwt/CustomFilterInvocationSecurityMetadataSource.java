package com.crm.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.crm.model.entity.SettingApiPermission;
import com.crm.service.setting.api.permission.ApiPermissionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	private final String []exeludePaths = {"/authenticate","/getUserInfo","/user/logout"};
	private final List<String> exeludePathList = Arrays.asList(exeludePaths);
	
	private final String []adminPaths = {"/actuator","/instances"};
	
	@Autowired
	private ApiPermissionService apiPermissionService;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();
		if(url.contains("?")) {
			url = url.substring(0,url.indexOf("?"));;
		}
		List<SettingApiPermission> apiList = apiPermissionService.findAll();
		List<String> urlRoles = new ArrayList<String>();
		for (SettingApiPermission index : apiList) {
			if (antPathMatcher.match(index.getId().getApi(), url)) {
				log.info(index.toString());
				urlRoles.add(index.getId().getRole());
			}
		}
		if (!urlRoles.isEmpty()) {
			String []arr = new String[urlRoles.size()];
			urlRoles.toArray(arr);
			return SecurityConfig.createList(arr);
		}
		if(exeludePathList.contains(url)) {
			return null;
		}
		
		for(String ignorePath : adminPaths) {
			if(url.startsWith(ignorePath)) {
				return null;
			}
		}
		
		return SecurityConfig.createList("BASIC");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
