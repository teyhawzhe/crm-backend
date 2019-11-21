package com.crm.service.setting.role.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.SettingRole;
import com.crm.repository.SettingRoleRepository;
import com.crm.restController.setting.role.AddForm;
import com.crm.service.setting.role.SettingRoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SettingRoleServiceImpl implements SettingRoleService {

	@Autowired
	private SettingRoleRepository roleRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(AddForm roleForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			SettingRole tbRole = new SettingRole();
			tbRole.setDef(roleForm.getDef());
			tbRole.setRole(roleForm.getRole());
			tbRole.setStatus(roleForm.isStatus());
			roleRepository.save(tbRole);
		} catch (Exception e) {
			throw new Exception("新增失敗!", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<SettingRole> getAllList() {
		// TODO Auto-generated method stub
		return roleRepository.findAll(Sort.by("role"));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(AddForm roleForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			roleRepository.update(roleForm.getDef(),roleForm.isStatus(), roleForm.getRole());
		} catch (Exception ex) {
			throw new Exception("更新失敗!", ex);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(AddForm roleForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			roleRepository.delete(roleForm.getRole());
		} catch (Exception ex) {
			throw new Exception("刪除失敗!", ex);
		}
	}

}
