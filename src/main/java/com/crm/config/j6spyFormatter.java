package com.crm.config;

import com.crm.utils.UserProfileUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class j6spyFormatter implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {
		if(null!=UserProfileUtils.getUsername() ) {
			return !"".equals(sql.trim()) ?  UserProfileUtils.getUsername() + "    " +sql : "no Sql";
		}
		return "";
	}

}
