package com.crm.sqlTools;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenSqlUtils {	
	
	public static String isNull(String col) {
		return" AND "+col+" IS NULL ";
	}
	
	public static String isNotNull(String col) {
		return" AND "+col+" IS NOT NULL ";
	}
	
	public static String equals(String col , String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+"=:"+ colTrans(col);
		}
		return "" ;
	}
	
	public static String equals(String col , String value , String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+"=:"+ selfDef;
		}
		return "" ;
	}
	
	public static String notEquals(String col , String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" != :"+ colTrans(col) +" ";
		}
		return "" ;
	}
	
	public static String notEquals(String col , String value, String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" != :"+ selfDef +" ";
		}
		return "" ;
	}
	
	public static String big(String col, String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" > :"+ colTrans(col) +" ";
		}
		return "" ;
	}
	
	public static String big(String col, String value, String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" > :"+ selfDef +" ";
		}
		return "" ;
	}
	
	public static String bigEqual(String col, String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" >= :"+ colTrans(col) +" ";
		}
		return "" ;
	}
	
	public static String bigEqual(String col, String value, String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" >= :"+ selfDef +" ";
		}
		return "" ;
	}
	
	public static String small(String col, String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" < :"+ colTrans(col) +" ";
		}
		return "" ;
	}
	
	public static String small(String col, String value, String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" < :"+ selfDef +" ";
		}
		return "" ;
	}
	
	public static String smallEqual(String col, String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" <= :"+ colTrans(col) +" ";
		}
		return "" ;
	}
	
	public static String smallEqual(String col, String value, String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" <= :"+ selfDef +" ";
		}
		return "" ;
	}
	
	
	public static String like(String col , String value) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" like :"+ colTrans(col) +" ";
		}
		return "" ;
	}
	
	public static String like(String col , String value, String selfDef) {
		if (!StringUtils.isBlank(value)) {
			return" AND "+col+" like :"+ selfDef +" ";
		}
		return "" ;
	}
	
	//
	public static String between(String col, String first, String last , String firstColName , String lastColName) {
		if (!StringUtils.isBlank(first) && !StringUtils.isBlank(last)) {
			return" AND "+col+" BETWEEN :"+ firstColName +" AND :"+lastColName+" ";
		}
		return "" ;
	}
	
	public static String colTrans(String colName) {
		
		if(StringUtils.isBlank(colName)){
			return null;
		}
		
		char[] colNameArray = colName.toCharArray();
		
		StringBuilder sb = new StringBuilder();

		for(int i=0 ; i<colNameArray.length ; i++) {
			int charType = Character.getType(colNameArray[i]);
			
			if(String.valueOf(colNameArray[i]).equals("_")) {
				i = i+1;
				String upper = String.valueOf(colNameArray[i]).toUpperCase();
				sb.append(upper);
				continue;
			}
			
			if(charType == Character.UPPERCASE_LETTER) {
				String lower = String.valueOf(colNameArray[i]).toLowerCase();
				sb.append(lower);
		    }else {
		    	sb.append(colNameArray[i]);
		    }
		}
		
		return sb.toString();
	}
	
}
