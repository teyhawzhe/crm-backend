package com.crm.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.CustForm;

@Repository
@Mapper
public interface TbCustBResposiory {
	public long checkPersonID(String personId);
	
	public List<TbCust> findAll(CustForm tbCust);
}
