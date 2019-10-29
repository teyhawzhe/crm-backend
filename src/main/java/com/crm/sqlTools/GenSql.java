package com.crm.sqlTools;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.crm.common.HttpStatus;
import com.crm.common.PageResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GenSql<T> implements GenSqlInterface<T> {

	@Autowired
	private EntityManager entityManager;

	private Class<?> clazz;
	
	private Query querydata;
	private Query queryCount;

	private StringBuilder dataSql = null;
	private StringBuilder countSql = null;
	private StringBuilder whereSql = null;
	private StringBuilder addWhereSql = null;
	private StringBuilder groupBySql = null;
	private StringBuilder orderSql = null;
	
	private boolean isMap = false; 
	
	private PageRequest pagable;

	
	//將所有的資料回歸預設
	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.dataSql = new StringBuilder();
		this.countSql = new StringBuilder();
		this.whereSql = new StringBuilder();
		this.addWhereSql = new StringBuilder();
		this.groupBySql = new StringBuilder();
		this.orderSql = new StringBuilder();
		this.isMap = false;
		
	}
	
	//將所有的資料回歸預設
	@Override
	public void init(Class<?> clazz, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		this.dataSql = new StringBuilder();
		this.countSql = new StringBuilder();
		this.whereSql = new StringBuilder();
		this.addWhereSql = new StringBuilder();
		this.groupBySql = new StringBuilder();
		this.orderSql = new StringBuilder();
		this.isMap = false;
		this.clazz = clazz;
		this.pagable = pageRequest;
	}
	
	//sql 語法 不含where 和 order by 
	@Override
	public void select(String value) {
		this.dataSql.append(value);
		this.countSql.append(value);
	}
	
	//sql 語法  客製化
	@Override
	public void addSql(String value) {
		// TODO Auto-generated method stub
		this.dataSql.append(value);
		this.countSql.append(value);
	}
 
	@Override
	public void addGroupBy(String value) {
		// TODO Auto-generated method stub
		this.groupBySql.append(value);
	}

	
	//orderBy語法  
	@Override
	public void orderBy(String value) {
		this.orderSql.append(value);
	}

	//sql與where,orderby給定value後，需要告知Query取得status
	@Override
	public void finish() {
		this.combine();
		
		if(!this.isMap) {
			this.querydata = entityManager.createNativeQuery(dataSql.toString(), clazz);
		}else {
			this.querydata = entityManager.createNativeQuery(dataSql.toString());
			this.querydata.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		this.queryCount = entityManager.createNativeQuery(dataSql.toString(), clazz);
	}

	//binding col 和 value
	@Override
	public void addParameter(String col, String value) {
		if (!StringUtils.isBlank(value)) {
			this.querydata.setParameter(GenSqlUtils.colTrans(col), value);
			this.queryCount.setParameter(GenSqlUtils.colTrans(col), value);
		}
	}

	//binding col 和 value , selfDef= true ,col=自定義欄位
	@Override
	public void addParameter(String col, String value, boolean selfDef) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			if(selfDef) {
				this.querydata.setParameter(col, value);
				this.queryCount.setParameter(col, value);
			}
		}
	}
	
	@Override
	public void addLikeParameter(String col, String value) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			this.querydata.setParameter(GenSqlUtils.colTrans(col), "%"+value+"%");
			this.queryCount.setParameter(GenSqlUtils.colTrans(col), "%"+value+"%");
		}
	}

	@Override
	public void addLikeParameter(String col, String value, boolean selfDef) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			if(selfDef) {
				this.querydata.setParameter(col, "%"+value+"%");
				this.queryCount.setParameter(col,"%"+ value+"%");
			}
		}
	}

	@Override
	public void addRLikeParameter(String col, String value) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			this.querydata.setParameter(GenSqlUtils.colTrans(col), value+"%");
			this.queryCount.setParameter(GenSqlUtils.colTrans(col), value+"%");
		}
	}

	@Override
	public void addRLikeParameter(String col, String value, boolean selfDef) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			if(selfDef) {
				this.querydata.setParameter(col, value+"%");
				this.queryCount.setParameter(col, value+"%");
			}
		}
	}

	@Override
	public void addLLikeParameter(String col, String value) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			this.querydata.setParameter(GenSqlUtils.colTrans(col), "%"+value);
			this.queryCount.setParameter(GenSqlUtils.colTrans(col), "%"+value);
		}
	}

	@Override
	public void addLLikeParameter(String col, String value, boolean selfDef) {
		// TODO Auto-generated method stub
		if (!StringUtils.isBlank(value)) {
			if(selfDef) {
				this.querydata.setParameter(col, "%"+value);
				this.queryCount.setParameter(col, "%"+value);
			}
		}
	}
	
	
	//組合 where  + order by 
	@Override
	public void combine() {
		// TODO Auto-generated method stub
		if(this.whereSql.toString().length()>0) {
			
			StringBuilder filter = new StringBuilder();
			filter.append(" WHERE ");
			filter.append(whereSql);
			filter.append(addWhereSql);
			
			String []str = filter.toString().split("\\s+");
			
			StringBuilder newSql = new StringBuilder();
			
			for(int i=0 ; i <str.length;i++) {
				if(str[i].equals("WHERE")) {
					newSql.append(str[i]+" ");
					if(str[i+1].equals("AND")) {
						i=i+1;
					}
				}else {
					newSql.append(str[i]+" ");
				}
			}
			
			log.info("newSql " + newSql.toString());
			
			this.dataSql.append(newSql);
			this.countSql.append(newSql);
		}
		
		if(this.groupBySql.toString().length()>0) {
			this.dataSql.append(" GROUP BY ");
			this.dataSql.append(groupBySql);
			this.countSql.append(" GROUP BY ");
			this.countSql.append(groupBySql);
		}
		
		if(this.orderSql.toString().length()>0) {
			this.dataSql.append(" ORDER BY ");
			this.dataSql.append(orderSql);
			this.countSql.append(" ORDER BY ");
			this.countSql.append(orderSql);
		}
		
	}
	
	//取得分頁
	@Override
	public PageResult<?> getPageResult() {

		PageResult<List<T>> pageResult = new PageResult<List<T>>(); 
		
		int total = this.queryCount.getResultList().size();
		log.info("total = "+total);
		if (total == 0) {
			pageResult.setStatus(HttpStatus.empty);
			pageResult.setMessage("查無資料!");
			pageResult.setData(null);
			pageResult.setCurrent(this.pagable.getPageNumber());
			pageResult.setTotal(0);
			pageResult.setPageSize(this.pagable.getPageSize());
		} else {
			if (null != this.pagable) {
				this.querydata.setFirstResult((int) this.pagable.getOffset());
				this.querydata.setMaxResults(this.pagable.getPageSize());
			}
			pageResult.setStatus(HttpStatus.ok);
			pageResult.setMessage("查詢成功!");
			pageResult.setData(this.querydata.getResultList());
			pageResult.setCurrent(this.pagable.getPageNumber());
			pageResult.setTotal(total);
			pageResult.setPageSize(this.pagable.getPageSize());
		}
		this.dataSql =  null;
		this.countSql = null;
		this.whereSql = null;
		this.groupBySql = null;
		this.orderSql = null;
		this.addWhereSql = null;
		return pageResult;
	}

	//取得sql字串
	@Override
	public String getDataSql() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		this.combine();
		sb.append(this.dataSql);
		return sb.toString();
	}

	@Override
	public void equal(String col, String value) {
		this.whereSql.append(GenSqlUtils.equals(col, value));
	}
	
	@Override
	public void equal(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.equals(col, value,selfDef));
	}
	
	@Override
	public void notEqual(String col, String value) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.notEquals(col, value));
	}

	@Override
	public void notEqual(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.notEquals(col, value,selfDef));
	}
	
	@Override
	public void like(String col, String value) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.like(col, value));
	}

	@Override
	public void like(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.like(col, value,selfDef));
	}

	@Override
	public void big(String col, String value) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.big(col, value));
	}

	@Override
	public void big(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.big(col, value,selfDef));
	}

	@Override
	public void bigEqual(String col, String value) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.bigEqual(col, value));
	}

	@Override
	public void bigEqual(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.bigEqual(col, value,selfDef));
	}

	@Override
	public void small(String col, String value) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.small(col, value));
	}

	@Override
	public void small(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.small(col, value,selfDef));
	}

	@Override
	public void smallEqual(String col, String value) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.smallEqual(col, value));
	}

	@Override
	public void smallEqual(String col, String value, String selfDef) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.smallEqual(col, value,selfDef));
	}

	@Override
	public void between(String col, String first, String last,String firstCol, String lastCol) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.between(col, first,last ,firstCol,lastCol));
	}

	@Override
	public void isNull(String col) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.isNull(col));
	}

	@Override
	public void isNotNull(String col) {
		// TODO Auto-generated method stub
		this.whereSql.append(GenSqlUtils.isNotNull(col));
	}

	@Override
	public void addWhere(String value) {
		// TODO Auto-generated method stub
		this.addWhereSql.append(" "+value+" ");
	}

	@Override
	public void isMapData() {
		// TODO Auto-generated method stub
		this.isMap = true;
	}

}
