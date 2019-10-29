package com.crm.common;

import java.util.HashMap;
import java.util.Map;

public class Variable {
	
	public static String admin = "ADMIN";
	public static String user = "USER";
	
	public static Map<String,String> region = new HashMap<String,String>();
	public static Map<String,String> custLevel = new HashMap<String,String>();
	
	
	static {
		region.put("TP", "台北");
		region.put("TZ", "台中");
		region.put("TN", "台南");
		region.put("TD", "台東");
		
		custLevel.put("1", "Normal");
		custLevel.put("2", "Middle");
		custLevel.put("3", "VIP");
	}
	
}
