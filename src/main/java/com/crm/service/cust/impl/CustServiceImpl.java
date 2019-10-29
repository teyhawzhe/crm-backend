package com.crm.service.cust.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.common.PageResult;
import com.crm.model.entity.TbCust;
import com.crm.model.entity.TbCustPK;
import com.crm.model.queryForm.cust.CustForm;
import com.crm.model.queryForm.cust.CustUpdateForm;
import com.crm.repository.CustRespository;
import com.crm.service.cust.CustService;
import com.crm.sqlTools.GenSql1;
import com.crm.sqlTools.GenSqlInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustServiceImpl implements CustService {

	@Autowired
	private CustRespository custRespository;
	
	@Autowired
	private GenSqlInterface<TbCust> gen;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional(readOnly = true)
	@Override
	public Long getSequence() {
		// TODO Auto-generated method stub
		return custRespository.getTbCustSeq();
	}

	@Transactional(readOnly = true)
	@Override
	public Long checkPersonIdByPersonId(String personId) {
		// TODO Auto-generated method stub
		return custRespository.checkPersonIdByPersonId(personId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<TbCust> getList() {
		// TODO Auto-generated method stub
		return custRespository.findAll();
	}

	@Override
	public void save(TbCust t) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(TbCust t) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TbCust> getList(Map<String, Object> params, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TbCust saveAndGetPk(TbCust t) throws Exception {
		// TODO Auto-generated method stub
		try {

			TbCustPK tbCustPK = t.getId();
			tbCustPK.setCustId(String.valueOf(this.getSequence()));
			t.setId(tbCustPK);

			return custRespository.saveAndFlush(t);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<TbCust> getList(CustForm custForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<TbCust> getList(CustForm custForm, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return custRespository.findAll(pageRequest);
	}
	
	@Transactional(readOnly = true)
	@Override
	public PageResult<List<Map<String, Object>>> getSqlMap(CustForm custForm, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		gen.init(TbCust.class,pageRequest);
		gen.isMapData();
		gen.select("SELECT * FROM TB_CUST");
		gen.like("CUST_ID", custForm.getCustId());
		gen.equal("REGION_ID", custForm.getRegionId());
		gen.equal("CUST_LEVEL", custForm.getCustLevel());
		gen.equal("ENG_NAME", custForm.getEngName());
		gen.equal("LOC_NAME", custForm.getLocName());
		gen.equal("PERSON_TYPE", custForm.getPersonType());
		gen.orderBy("CUST_ID");
		gen.finish();
		
		gen.addParameter("CUST_ID1", "%"+custForm.getCustId());
		gen.addParameter("REGION_ID", custForm.getRegionId());
		gen.addParameter("CUST_LEVEL", custForm.getCustLevel());
		gen.addParameter("ENG_NAME", custForm.getEngName());
		gen.addParameter("LOC_NAME", custForm.getLocName());
		gen.addParameter("PERSON_TYPE", custForm.getPersonType());

		return  (PageResult<List<Map<String, Object>>>) gen.getPageResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public PageResult<List<TbCust>> getSql(CustForm custForm, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		/*
		GenSql1 genSql2 = new GenSql1(entityManager);
		genSql2.add("SELECT COUNT(1) FROM TB_CUST where 1=1 ");
		genSql2.add("and CUST_ID = :custID",custForm.getCustId());
		genSql2.add("and REGION_ID = :regionId",custForm.getRegionId());
		
		int total = ((BigInteger) genSql2.getCountResult()).intValue();
		log.info("total total " + total);
		*/
		/*
		GenSql1 genSql3 = new GenSql1(entityManager);
		genSql3.setClazz(TbCust.class);
		genSql3.add("SELECT * FROM TB_CUST where 1=1 ");
		genSql3.add("and CUST_ID = :custID and REGION_ID = :regionId ",custForm.getCustId(),custForm.getRegionId());
		
		List<TbCust> result = (List<TbCust>) genSql3.getResult();
		
		log.info("result size = " +result.size());*/
		
		GenSql1 genSql1 = new GenSql1(entityManager);
		genSql1.setModel(TbCust.class);
		genSql1.setPageRequest(pageRequest);
		genSql1.add("SELECT * FROM TB_CUST where 1=1 ");
		genSql1.add("and CUST_ID = :custID",custForm.getCustId());
		genSql1.add("and REGION_ID = :regionId",custForm.getRegionId());
		
		return (PageResult<List<TbCust>>) genSql1.getPageResult();
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TbCust update(CustUpdateForm custUpdateForm) {
		// TODO Auto-generated method stub
		TbCustPK id = new TbCustPK();
		id.setCustId(custUpdateForm.getCustId());
		id.setRegionId(custUpdateForm.getRegionId());
		TbCust cust = custRespository.getOne(id);

		if (!StringUtils.isBlank(custUpdateForm.getCustLevel())) {
			cust.setCustLevel(custUpdateForm.getCustLevel());
		}
		if (!StringUtils.isBlank(custUpdateForm.getEngName())) {
			cust.setEngName(custUpdateForm.getEngName());
		}
		if (!StringUtils.isBlank(custUpdateForm.getLocName())) {
			cust.setLocName(custUpdateForm.getLocName());
		}
		if (!StringUtils.isBlank(custUpdateForm.getPersonType())) {
			cust.setPersonType(custUpdateForm.getPersonType());
		}
		if (!StringUtils.isBlank(custUpdateForm.getPersonId())) {
			cust.setPersonId(custUpdateForm.getPersonId());
		}
		return custRespository.save(cust);
	}

	@Override
	public long checkPersonIdByPersonIdNotIn(String personId, String custId, String regionId) {
		// TODO Auto-generated method stub
		return custRespository.checkPersonIdByPersonIdNotIn(personId, custId, regionId);
	}

}
