package com.crm.sqlTools;

import org.springframework.data.domain.PageRequest;

import com.crm.common.PageResult;

public interface GenSqlInterface<T> {
	
	public void init();
	public void init(Class<?> clazz,PageRequest pageRequest);
	public void select(String value);
	public void addSql(String value);
	public void addWhere(String value);
	public void addGroupBy(String value);
	public void orderBy(String value);
	public void finish();
	public void addParameter(String col, String value);
	public void addParameter(String col, String value,boolean selfDef);
	
	public void addLikeParameter(String col, String value);
	public void addLikeParameter(String col, String value,boolean selfDef);
	
	public void addRLikeParameter(String col, String value);
	public void addRLikeParameter(String col, String value,boolean selfDef);
	
	public void addLLikeParameter(String col, String value);
	public void addLLikeParameter(String col, String value,boolean selfDef);
	
	public void isMapData();
	
	public void combine();
	
	public PageResult<?> getPageResult();
	public String getDataSql();
	
	public void isNull(String col);
	public void isNotNull(String col);
	
	public void equal(String col, String value);
	public void equal(String col, String value, String selfDef);
	
	public void notEqual(String col, String value);
	public void notEqual(String col, String value, String selfDef);
	
	public void like(String col, String value);
	public void like(String col, String value, String selfDef);
	
	public void big(String col, String value);
	public void big(String col, String value, String selfDef);
	
	public void bigEqual(String col, String value);
	public void bigEqual(String col, String value, String selfDef);
	
	public void small(String col, String value);
	public void small(String col, String value, String selfDef);
	
	public void smallEqual(String col, String value);
	public void smallEqual(String col, String value, String selfDef);
	
	public void between(String col, String first, String last,String firstCol, String lastCol);

}
