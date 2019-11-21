package com.crm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Data
public class DataManager {

	private EntityManager entityManager;

	private boolean isMap;
	private PageRequest pagable;

	private StringBuilder sql;
	private Map<String, Object> parameters;

	private Query queryData;
	private Query queryCount;
	private Class<?> clazz;

	public DataManager(EntityManager entityManager) {
		this.sql = new StringBuilder();
		this.parameters = new HashMap<String, Object>();
		this.pagable = null;
		this.isMap = false;
		this.clazz = null;
		this.entityManager = entityManager;
	}

	public void setModel(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setPageRequest(PageRequest pagable) {
		this.pagable = pagable;
	}

	public Object getSingleResult() {
		this.queryDataInit();
		return this.queryData.getSingleResult();
	}

	public Object getCountResult() {
		this.init();
		return this.queryData.getSingleResult();
	}

	public List<?> getResult() {
		int total = this.getTotal();
		if (total == 0) {
			return null;
		}
		this.queryDataInit();
		return this.queryData.getResultList();
	}

	public PageResult<?> getPageResult() {

		PageResult<List<?>> pageResult = new PageResult<List<?>>();
		int total = getTotal();

		if (total == 0) {
			pageResult.setStatus(HttpStatus.empty);
			pageResult.setMessage("查無資料!");
			pageResult.setData(null);
			pageResult.setCurrent(this.pagable.getPageNumber());
			pageResult.setTotal(0);
			pageResult.setPageSize(this.pagable.getPageSize());
		} else {

			this.queryDataInit();

			if (null != this.pagable) {
				this.queryData.setFirstResult((int) this.pagable.getOffset());
				this.queryData.setMaxResults(this.pagable.getPageSize());
			} else {

			}
			pageResult.setStatus(HttpStatus.ok);
			pageResult.setMessage("查詢成功!");
			pageResult.setData(this.queryData.getResultList());
			pageResult.setCurrent(this.pagable.getPageNumber());
			pageResult.setTotal(total);
			pageResult.setPageSize(this.pagable.getPageSize());
		}

		return pageResult;
	}

	public int getTotal() {
		this.countSqlInit();
		int total = this.queryCount.getResultList().size();
		log.info("total = " + total);
		return total;
	}

	public int executeUpdate() {
		this.init();
		return this.queryData.executeUpdate();
	}

	public void add(Object... ob) {
		if (ob.length == 1) {
			this.sql.append(" " + String.valueOf(ob[0]) + " ");
		} else {
			this.combine(ob);
		}
	}

	private void countSqlInit() {
		this.queryCount = this.entityManager.createNativeQuery(this.sql.toString());
		this.genParameter(queryCount);
	}

	private void queryDataInit() {
		if (isMap || null == clazz) {
			this.queryData = this.entityManager.createNativeQuery(this.sql.toString());
			this.queryData.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		} else {
			this.queryData = entityManager.createNativeQuery(sql.toString(), clazz);
		}
		this.genParameter(queryData);
	}

	private void init() {
		this.queryData = this.entityManager.createNativeQuery(this.sql.toString());
		this.genParameter(queryData);
	}

	private void genParameter(Query query) {
		for (String index : this.parameters.keySet()) {
			query.setParameter(index, parameters.get(index));
		}
	}

	private void combine(Object[] ob) {
		boolean hasValue = true;
		String condition = null;

		for (int i = 0; i < ob.length; i++) {
			if (i == 0) {
				condition = String.valueOf(ob[0]);
			} else {
				hasValue = this.validIsNotNull(ob[i]);
				if (!hasValue) {
					break;
				}
			}
		}

		if (hasValue) {
			this.sql.append(" " + condition + " ");
			this.match(ob);
		}

	}

	private void match(Object[] ob) {

		String[] cursor = String.valueOf(ob[0]).split("\\s+");
		List<String> variable = new ArrayList<String>();
		for (int i = 0; i < cursor.length; i++) {
			if (cursor[i].startsWith(":")) {
				variable.add(cursor[i].substring(1, cursor[i].length()));
			}
		}

		if (variable.size() != ob.length - 1) {
			log.info("查詢條件與parameter不相同!!");
		} else {
			for (int i = 0; i < variable.size(); i++) {

				if (ob[i + 1] instanceof String) {
					this.parameters.put(variable.get(i), String.valueOf(ob[i + 1]));
				} else if (ob[i + 1] instanceof Integer) {
					this.parameters.put(variable.get(i), Integer.parseInt(String.valueOf(ob[i + 1])));
				} else if (ob[i + 1] instanceof Long) {
					this.parameters.put(variable.get(i), Long.parseLong(String.valueOf(ob[i + 1])));
				} else if (ob[i + 1] instanceof Double) {
					this.parameters.put(variable.get(i), Double.parseDouble(String.valueOf(ob[i + 1])));
				} else if (ob[i + 1] instanceof Float) {
					this.parameters.put(variable.get(i), Float.parseFloat(String.valueOf(ob[i + 1])));
				} else if (ob[i + 1] instanceof Boolean) {
					this.parameters.put(variable.get(i), Boolean.parseBoolean(String.valueOf(ob[i + 1])));
				} else {
					this.parameters.put(variable.get(i), ob[i + 1]);
				}

			}
		}

	}

	private boolean validIsNotNull(Object ob) {
		if (null == ob || StringUtils.isBlank(String.valueOf(ob))) {
			return false;
		}
		return true;
	}

}
