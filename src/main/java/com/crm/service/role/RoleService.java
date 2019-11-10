package com.crm.service.role;

import java.util.List;

import com.crm.model.entity.TbRole;
import com.crm.restController.role.RoleForm;

public interface RoleService {
	public void save(RoleForm roleForm) throws Exception;
	public List<TbRole> getAllList();
	public void update(RoleForm roleForm) throws Exception;
	public void delete(RoleForm roleForm) throws Exception;
}
