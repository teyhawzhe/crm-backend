package com.crm.model.dto.cust;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6685809659261168001L;

	private String custId;
	private String regionId;
	private String regionIdName;
	private String personType;
	private String locName;
	private String engName;
	private String custLevel;
	private String custLevelName;
	private String personId;
}
