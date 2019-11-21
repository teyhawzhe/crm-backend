package com.crm.service.setting.role;

import java.util.List;

import com.crm.model.entity.SettingRole;
import com.crm.restController.setting.role.AddForm;

public interface SettingRoleService {
	public void save(AddForm roleForm) throws Exception;
	public List<SettingRole> getAllList();
	public void update(AddForm roleForm) throws Exception;
	public void delete(AddForm roleForm) throws Exception;
}
