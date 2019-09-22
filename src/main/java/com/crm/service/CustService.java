package com.crm.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.crm.common.PageResult;
import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.CustForm;

public interface CustService {
	
	public TbCust saveAndGetPk(TbCust tbCust) throws Exception;
	
	public List<TbCust> getList();
	//@PreAuthorize("hasAuthority('ADMINS')")
	public PageResult<List<TbCust>> getList(CustForm custForm , PageRequest pageRequest);
	
	public Long getSequence();
	
	public Long checkPersonID(String personId);
	
	
}
