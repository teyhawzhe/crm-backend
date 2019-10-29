package com.crm.model.queryForm.cust;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8949943429795738552L;
	
	private String custId;
	private String regionId;
	private String personType;
	private String locName;
	private String engName;
	private String custLevel;
	private String personId;
	
}


