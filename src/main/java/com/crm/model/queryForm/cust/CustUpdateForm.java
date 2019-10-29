package com.crm.model.queryForm.cust;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustUpdateForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6685809659261168001L;

	@Size(min = 1 , max = 10 ,message = "客戶代號不能為NULL")
	private String custId;
	
	@Size(min = 2 , max = 2 ,message = "地區代號不能為NULL")
	private String regionId;
	
	@Size(min = 1 , max = 1 ,message = "客戶類別不能為NULL")
	private String personType;
	
	@Size(min = 1 , max = 200 ,message = "本地名字不能為NULL")
	private String locName;
	
	@Size(min = 1 , max = 200 ,message = "英文名字不能為NULL")
	private String engName;
	
	@Size(min = 1 , max = 1 ,message = "客戶等級不能為NULL")
	private String custLevel;

	@Size(min = 10 , max = 10 , message = "身分證長度只能有10個字元")
	private String personId;
}
