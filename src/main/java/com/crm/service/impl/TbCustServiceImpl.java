package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.CustForm;
import com.crm.repository.hibernate.TbCustHRespository;
import com.crm.repository.mybatis.TbCustBResposiory;
import com.crm.service.TbCustService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TbCustServiceImpl implements TbCustService{

	@Autowired
	private TbCustHRespository tbCustHRespository;
	
	@Autowired
	private TbCustBResposiory tbCustBRespository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TbCust saveAndGetPk(TbCust tbCust) throws Exception {
		// TODO Auto-generated method stub	
		try {
			return tbCustHRespository.saveAndFlush(tbCust);
		}catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<TbCust> getList(CustForm tbCust){
		// TODO Auto-generated method stub	
		return tbCustBRespository.findAll(tbCust);
	}

	@Transactional(readOnly = true)
	@Override
	public Long getSequence() {
		// TODO Auto-generated method stub
		return tbCustHRespository.getTbCustSeq();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Long checkPersonID(String personId) {
		// TODO Auto-generated method stub
		return tbCustBRespository.checkPersonID(personId);
	}

	@Override
	public List<TbCust> getList() {
		// TODO Auto-generated method stub
		return null;
	}

}
