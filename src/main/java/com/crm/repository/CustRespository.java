package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.TbCust;
import com.crm.model.entity.TbCustPK;

@Repository
public interface CustRespository extends JpaRepository<TbCust, TbCustPK>, JpaSpecificationExecutor<TbCust>{

	@Query(nativeQuery = true, value = "select SEQ_CUST_ID.nextval as seq from dual")
	public long getTbCustSeq();
	
	@Query(nativeQuery = true, value = "select count(*) from tb_cust where PERSON_ID=:personId")
	public long checkPersonID(String personId);
	
}
