package com.crm.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.common.HttpStatus;
import com.crm.common.PageResult;
import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.CustForm;
import com.crm.repository.CustRespository;
import com.crm.service.CustService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustServiceImpl implements CustService {

	@Autowired
	private CustRespository tbCustHRespository;

	@Autowired
	private EntityManager entityManager;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TbCust saveAndGetPk(TbCust tbCust) throws Exception {
		// TODO Auto-generated method stub
		try {
			return tbCustHRespository.saveAndFlush(tbCust);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Long getSequence() {
		// TODO Auto-generated method stub
		return tbCustHRespository.getTbCustSeq();
	}

	@Override
	public List<TbCust> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public PageResult<List<TbCust>> getList(CustForm custForm, PageRequest pageRequest) {
		// TODO Auto-generated method stub

		// entityManager
		StringBuilder resultSql = new StringBuilder();
		StringBuilder countSql = new StringBuilder();
		StringBuilder where = new StringBuilder();
		resultSql.append("SELECT * FROM TB_CUST");
		countSql.append("SELECT COUNT(*) FROM TB_CUST");
		where.append(" WHERE 1=1 ");
		
		if (!StringUtils.isBlank(custForm.getCustId())) {
			where.append(" AND CUST_ID = :custId");
		}
		if (!StringUtils.isBlank(custForm.getRegionId())) {
			where.append(" AND REGION_ID = :regionId");
		}
		if (!StringUtils.isBlank(custForm.getCustLevel())) {
			where.append(" AND CUST_LEVEL = :custLevel");
		}
		if (!StringUtils.isBlank(custForm.getEngName())) {
			where.append(" AND ENG_NAME = :engName");
		}
		if (!StringUtils.isBlank(custForm.getLocName())) {
			where.append(" AND LOC_NAME = :locName");
		}
		if (!StringUtils.isBlank(custForm.getPersonType())) {
			where.append(" AND PERSON_TYPE = :personTyle");
		}
		resultSql.append(where);
		countSql.append(where);
		
		Query data = entityManager.createNativeQuery(resultSql.toString(), TbCust.class);
		Query count = entityManager.createNativeQuery(countSql.toString());
		
		if (!StringUtils.isBlank(custForm.getCustId())) {
			data.setParameter("custId", custForm.getCustId());
			count.setParameter("custId", custForm.getCustId());
		}
		if (!StringUtils.isBlank(custForm.getRegionId())) {
			data.setParameter("regionId", custForm.getRegionId());
			count.setParameter("regionId", custForm.getRegionId());
		}
		if (!StringUtils.isBlank(custForm.getCustLevel())) {
			data.setParameter("custLevel", custForm.getCustLevel());
			count.setParameter("custLevel", custForm.getCustLevel());
		}
		if (!StringUtils.isBlank(custForm.getEngName())) {
			data.setParameter("engName", custForm.getEngName());
			count.setParameter("engName", custForm.getEngName());
		}
		if (!StringUtils.isBlank(custForm.getLocName())) {
			data.setParameter("locName", custForm.getLocName());
			count.setParameter("locName", custForm.getLocName());
		}
		if (!StringUtils.isBlank(custForm.getPersonType())) {
			data.setParameter("personTyle", custForm.getPersonType());
			count.setParameter("personTyle", custForm.getPersonType());
		}
		
		log.info("count = " + data.getMaxResults());
		
		PageResult<List<TbCust>> page = new PageResult<List<TbCust>>();
		List<TbCust> result = new ArrayList<TbCust>();
		
		
		
		BigInteger tota = (BigInteger) count.getSingleResult();
		long total = tota.longValue();
		log.info("total=>"+total);
		if(total>0) {
			data.setFirstResult((int) pageRequest.getOffset());
			data.setMaxResults(pageRequest.getPageSize());
			result = data.getResultList();
			page.setStatus(HttpStatus.ok);
			page.setMessage("查詢成功!");
			page.setTotal(total);
			page.setPageSize(result.size());
			log.info("pageRequest.getPageNumber() "+pageRequest.getPageNumber());
			page.setCurrent(pageRequest.getPageNumber()+1);
			page.setData(result);
		}else {
			page.setStatus(HttpStatus.empty);
			page.setMessage("查無資料!");
			page.setTotal(1);
			page.setPageSize(1);
			page.setCurrent(1);
			page.setData(result);
		}
		
		return page;
	}

	@Override
	public Long checkPersonID(String personId) {
		// TODO Auto-generated method stub
		return null;
	}

}
