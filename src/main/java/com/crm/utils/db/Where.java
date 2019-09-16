package com.crm.utils.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode
public class Where implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6851599371042716223L;

	private List<SqlConditionItem> condition = new ArrayList<SqlConditionItem>();
	
	public void add(SqlConditionItem item) {
		condition.add(item);
	}
	
}
