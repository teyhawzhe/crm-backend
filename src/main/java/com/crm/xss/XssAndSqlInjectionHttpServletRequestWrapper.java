package com.crm.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XssAndSqlInjectionHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssAndSqlInjectionHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXssString(value);
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value = super.getParameter(name);
		if (value == null) {
			return null;
		}
		return cleanXssString(value);
	}

	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXssString(values[i]);
		}
		return encodedValues;
	}

	private String cleanXssString(String value) {

		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		value = value.replaceAll("[*]", "[" + "*]");
		value = value.replaceAll("[+]", "[" + "+]");
		value = value.replaceAll("[?]", "[" + "?]");
		String[] values = value.split(" ");
		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|"
				+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
				+ "table|from|grant|use|group_concat|column_name|"
				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
				+ "chr|mid|master|truncate|char|declare|or|;|-|--|,|like|//|/|%|#";
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			for (int j = 0; j < values.length; j++) {
				if (values[j].equalsIgnoreCase(badStrs[i])) {
					values[j] = "forbid";
					log.info("cleanXSS String  " + value);
					log.info("cleanXSS String tranfer  " + values[j]);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i == values.length - 1) {
				sb.append(values[i]);
			} else {
				sb.append(values[i] + " ");
			}
		}
		value = sb.toString();
		return value;

	}

}
