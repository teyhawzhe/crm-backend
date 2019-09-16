package com.crm.utils.db;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.crm.exception.SqlConditionItemException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SqlCondition<T> {

	private Where where;
	private Specification specification;
	private final Class<T> typeParameterClass;
	
	
	
	public SqlCondition(Class<T> typeParameterClass , Where where) throws SqlConditionItemException{
		this.specification = genWhere(where);
		this.typeParameterClass = typeParameterClass;
	}
	public Specification<T> genWhere(Where where) throws SqlConditionItemException{
		
		Specification<T> spec = null;
		for(SqlConditionItem item : where.getCondition()) {
			
			boolean isEmpty = false;
			
			if (null == item.getValue()) {
				isEmpty = true;
			} else {
				/*if ("null".equals(String.valueOf(item.getValue()))) {
					isEmpty = true;
				}*/
				if (StringUtils.isBlank(String.valueOf(item.getValue()))) {
					isEmpty = true;
				}

			}
			if (item.isRequired()) {
				if (isEmpty) {
					log.info("========================isEmpty======================");
					throw new SqlConditionItemException(item.getName() + " can't not be null");
				}
			}
			if (!isEmpty) {
				
				if(spec==null) {
					spec=genCondition(item);
				}else {
					
					String prepend = null==item.getPrepend() ? "and" : item.getPrepend();
					if("and".equals(prepend)) {
						spec =spec.and(genCondition(item));
					}else if("or".equals(prepend)) {
						spec =spec.or(genCondition(item));
					}
					
				}
				
			}
			
			
			
		}
	
		return spec;
	}
	
	public Specification<T> genCondition(SqlConditionItem item){
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub	
					Predicate predicate=null;
						String operation = item.getOperation() == null ? "=" : item.getOperation();
						log.info("operation =>"+operation);
						OperationUtils<T> operationHandle = new OperationUtils<T>(item, root, criteriaBuilder);
						switch (operation) {
						case "=":
							predicate=operationHandle.equal();
							break;
						case "!=":
							predicate=operationHandle.notEqual();
							break;	
						case ">=":
							predicate=operationHandle.greaterThanOrEqualTo();
							break;
						case ">":
							predicate=operationHandle.greaterThan();
							break;
						case "<":
							predicate=operationHandle.lessThan();
							break;
						case "<=":
							predicate=operationHandle.lessThanOrEqualTo();
							break;
						case "like":
							predicate=operationHandle.like();
							break;
						case "rlike":
							predicate=operationHandle.rlike();
							break;
						case "llike":
							predicate=operationHandle.llike();
							break;
						case "between":
							predicate=operationHandle.between();
							break;
						default:
							throw new IllegalArgumentException("Operation is wrong!");
						}
						 
						query.where(predicate);
					
				return criteriaBuilder.and(predicate);
			}
		};
	}


}
