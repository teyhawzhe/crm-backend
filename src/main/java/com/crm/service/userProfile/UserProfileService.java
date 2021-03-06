package com.crm.service.userProfile;

import java.util.List;
import java.util.Map;

import com.crm.model.queryForm.setting.userRegister.UserRegisterForm;

public interface UserProfileService {
	public void save(UserRegisterForm userRegisterForm) throws Exception;
	public List<Map<String,Object>> getList(Map<String,Object> params);
	public void delete(String username) throws Exception;
	public void update(UserRegisterForm userRegisterForm) throws Exception;
	public String getImage();
}
