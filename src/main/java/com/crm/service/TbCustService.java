package com.crm.service;

import java.util.List;

import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.CustForm;

public interface TbCustService {
	public TbCust saveAndGetPk(TbCust tbCust) throws Exception;
	
	public List<TbCust> getList();
	
	public List<TbCust> getList(CustForm custForm);
	
	public Long getSequence();
	
	public Long checkPersonID(String personId);
}
