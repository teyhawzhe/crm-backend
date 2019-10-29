package com.crm.utils;

import java.io.Reader;
import java.sql.Clob;

public class ClobToString {

	public static String convert(Clob clob) {
		if (clob == null)
			return null;
		StringBuffer sb = new StringBuffer(65535);
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}
		} catch (Exception ex) {
			sb = null;
		} finally {
			try {
				if (clobStream != null) {
					clobStream.close();
				}
			} catch (Exception e) {
			}
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}

}
