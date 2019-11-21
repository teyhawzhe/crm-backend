package com.crm.service.userProfile.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.common.DataManager;
import com.crm.jwt.model.Roles;
import com.crm.jwt.model.RolesPk;
import com.crm.jwt.model.Users;
import com.crm.jwt.repository.RolesRepository;
import com.crm.jwt.repository.UsersRepository;
import com.crm.model.entity.UserProfile;
import com.crm.model.queryForm.setting.userRegister.UserRegisterForm;
import com.crm.repository.UserProfileRepository;
import com.crm.service.userProfile.UserProfileService;
import com.crm.utils.LoginUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private UsersRepository usersRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(UserRegisterForm userRegisterForm) throws Exception {
		try {
			// TODO Auto-generated method stub
			userRegisterForm.setUsername(userRegisterForm.getUsername().toLowerCase());
			log.info("userRegisterForm 1 " + userRegisterForm.getUsername());
			UserProfile userProfile = new UserProfile();
			userProfile.setUsername(userRegisterForm.getUsername());
			userProfile.setName(userRegisterForm.getName());
			userProfile.setImage(userRegisterForm.getImage());
			userProfile.setCreateDate(DateFormatUtils.format(new Date(), "yyyyMMdd HHmm"));
			userProfile.setCreater(LoginUser.getUsername());
			userProfileRepository.save(userProfile);
			log.info("userRegisterForm 2 " + userRegisterForm.getUsername());
			Users user = new Users();
			user.setUsername(userRegisterForm.getUsername());
			user.setPassword(passwordEncoder.encode(userRegisterForm.getUsername()));
			user.setEnable(true);
			usersRepository.save(user);
			log.info("userRegisterForm 3 " + userRegisterForm.getUsername());		
			List<Roles> rolesList = new ArrayList<Roles>();	
			for (String index : userRegisterForm.getRoles()) {
				RolesPk id = new RolesPk();
				id.setUsername(userRegisterForm.getUsername());
				id.setRole(index);
				Roles roles = new Roles();
				roles.setId(id);
				rolesList.add(roles);
			}
			rolesRepository.saveAll(rolesList);
			log.info("userRegisterForm 4 " + userRegisterForm.getUsername());
		} catch (Exception e) {
			throw new Exception("新增失敗!",e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		DataManager dataManager = new DataManager(entityManager);
		dataManager.add("SELECT A.*,B.ENABLE FROM USER_PROFILE A LEFT JOIN USERS B ON  A.USERNAME=B.USERNAME WHERE 1=1");
		for(String index : params.keySet()) {
			if(!StringUtils.isBlank(String.valueOf(params.get(index)))) {
				if(!index.equals("role")) {
					dataManager.add("AND A."+index.toUpperCase()+" = :"+index+" ",params.get(index));
				}
			}
		}
		dataManager.add(" ORDER BY A.USERNAME");
		List<Map<String, Object>> result = (List<Map<String, Object>>) dataManager.getResult();
		for( Map<String, Object> index : result) {
			DataManager subDataManager = new DataManager(entityManager);
			subDataManager.add("SELECT * FROM ROLES WHERE USERNAME = :username",index.get("USERNAME"));
			subDataManager.add("AND ROLE = :role",params.get("role"));
			List<Map<String, Object>> subResult = (List<Map<String, Object>>) subDataManager.getResult();
			String roles="";
			for( Map<String, Object> subIndex : subResult) {
				if(roles.length()>0) {
					roles+=",";
				}
				roles+=subIndex.get("ROLE");
			}
			index.put("ROLE", roles);
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(String username) throws Exception {
		// TODO Auto-generated method stub
		try {
			userProfileRepository.delete(username);
			rolesRepository.delete(username);
		}catch(Exception ex) {
			throw new Exception("刪除失敗!",ex);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(UserRegisterForm userRegisterForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			UserProfile userProfile = new UserProfile();
			userProfile.setUsername(userRegisterForm.getUsername());
			userProfile.setName(userRegisterForm.getName());
			userProfile.setImage(userRegisterForm.getImage());
			userProfileRepository.save(userProfile);
			List<Roles> rolesList = new ArrayList<Roles>();	
			for (String index : userRegisterForm.getRoles()) {
				RolesPk id = new RolesPk();
				id.setUsername(userRegisterForm.getUsername());
				id.setRole(index);
				Roles roles = new Roles();
				roles.setId(id);
				rolesList.add(roles);
			}
			rolesRepository.deleteUsernameAndExcludeRole(userRegisterForm.getUsername(), userRegisterForm.getRoles());
			rolesRepository.saveAll(rolesList);	
		}catch(Exception ex) {
			throw new Exception("更新失敗!",ex);
		}
	}

}
