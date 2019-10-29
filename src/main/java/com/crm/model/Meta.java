package com.crm.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Meta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3891479513396623501L;

	private String title;

	private String icon;
}
