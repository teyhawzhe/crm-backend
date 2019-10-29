package com.crm.service.cust;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.crm.common.PageResult;
import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.cust.CustForm;
import com.crm.model.queryForm.cust.CustUpdateForm;
import com.crm.service.CommonService;

public interface CustService extends CommonService<TbCust> {

	public List<TbCust> getList();

	public List<TbCust> getList(CustForm custForm);

	public Page<TbCust> getList(CustForm custForm, PageRequest pageRequest);

	public Long getSequence();

	public Long checkPersonIdByPersonId(String personId);
	
	public long checkPersonIdByPersonIdNotIn(String personId, String custId, String regionId);
	
	public PageResult<List<TbCust>> getSql(CustForm custForm, PageRequest pageRequest);
	
	public PageResult<List<Map<String, Object>>> getSqlMap(CustForm custForm, PageRequest pageRequest);
	
	public TbCust update(CustUpdateForm custUpdateForm);
}
