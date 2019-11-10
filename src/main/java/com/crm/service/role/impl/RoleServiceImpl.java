package com.crm.service.role.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.TbRole;
import com.crm.repository.RoleRepository;
import com.crm.restController.role.RoleForm;
import com.crm.service.role.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(RoleForm roleForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			TbRole tbRole = new TbRole();
			tbRole.setDef(roleForm.getDef());
			tbRole.setRole(roleForm.getRole());
			roleRepository.save(tbRole);
		} catch (Exception e) {
			throw new Exception("新增失敗!", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<TbRole> getAllList() {
		// TODO Auto-generated method stub
		return roleRepository.findAll(Sort.by("id"));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(RoleForm roleForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			roleRepository.update(roleForm.getDef(), roleForm.getRole());
		} catch (Exception ex) {
			throw new Exception("更新失敗!", ex);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(RoleForm roleForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			roleRepository.delete(roleForm.getRole());
		} catch (Exception ex) {
			throw new Exception("刪除失敗!", ex);
		}
	}

}
