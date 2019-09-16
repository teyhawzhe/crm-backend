package com.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

public interface CommonService<T> {
	public void save(T t);
	public T saveAndGetPk(T t);
	public void delete(T t);
	public List<T> getList(Map<String,Object> params ,Pageable pageable);
}
