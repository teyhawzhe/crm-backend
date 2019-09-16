package com.crm.utils.db;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class SqlConditionItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 149168523891941585L;
	private String embedId;
	private String name;
	private boolean required;
	private String value;
	private String type;
	private String prepend;
	private String operation;
	private String between;
}
