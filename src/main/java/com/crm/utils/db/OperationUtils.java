package com.crm.utils.db;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class OperationUtils<T> {

	private SqlConditionItem item;

	private Root<?> root;

	private CriteriaBuilder criteriaBuilder;

	private Path genQueryCol() {
		if (null == item.getEmbedId()) {
			return root.get(item.getName());
		} else {
			return root.get(item.getEmbedId()).get(item.getName());
		}
	}

	public Predicate equal() {
		Predicate predicate = criteriaBuilder.equal(genQueryCol(), item.getValue());
		return predicate;
	}
	
	public Predicate notEqual() {
		Predicate predicate = criteriaBuilder.notEqual(genQueryCol(), item.getValue());
		return predicate;
	}

	public Predicate greaterThanOrEqualTo() {
		Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(genQueryCol(),
				Integer.parseInt(String.valueOf(item.getValue())));
		return predicate;
	}
	public Predicate greaterThan() {
		Predicate predicate = criteriaBuilder.greaterThan(genQueryCol(),
				Integer.parseInt(String.valueOf(item.getValue())));
		return predicate;
	}

	public Predicate lessThan() {
		Predicate predicate = criteriaBuilder.lessThan(genQueryCol(),
				Integer.parseInt(String.valueOf(item.getValue())));
		return predicate;
	}

	public Predicate lessThanOrEqualTo() {
		Predicate predicate = criteriaBuilder.lessThanOrEqualTo(genQueryCol(),
				Integer.parseInt(String.valueOf(item.getValue())));
		return predicate;
	}

	public Predicate like() {
		Predicate predicate = criteriaBuilder.like(genQueryCol(),
				"%"+String.valueOf(item.getValue())+"%");
		return predicate;
	}

	public Predicate rlike() {
		Predicate predicate = criteriaBuilder.like(genQueryCol(),
				String.valueOf(item.getValue())+"%");
		return predicate;
	}

	public Predicate llike() {
		Predicate predicate = criteriaBuilder.like(genQueryCol(),
				"%"+String.valueOf(item.getValue()));
		return predicate;
	}

	public Predicate between() {
		Predicate predicate = criteriaBuilder.between(genQueryCol(), String.valueOf(item.getValue()) , String.valueOf(item.getBetween()));
		return predicate;
	}

}
