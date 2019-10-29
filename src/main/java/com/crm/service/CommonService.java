package com.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

public interface CommonService<T> {
	public void save(T t) throws Exception;
	public T saveAndGetPk(T t) throws Exception;
	public void delete(T t) throws Exception;
	public List<T> getList(Map<String,Object> params ,Pageable pageable);
}
