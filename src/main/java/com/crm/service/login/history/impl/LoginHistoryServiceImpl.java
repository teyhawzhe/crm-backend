package com.crm.service.login.history.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.LoginHistory;
import com.crm.model.entity.LoginHistoryPk;
import com.crm.repository.LoginHistoryRepository;
import com.crm.service.login.history.LoginHistoryService;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	@Transactional(noRollbackFor = Exception.class)
	@Override
	public void save(String username, boolean success, String reason) {
		// TODO Auto-generated method stub
		LoginHistory loginHistory = new LoginHistory();
		LoginHistoryPk loginHistoryPk = new LoginHistoryPk();
		loginHistoryPk.setUsername(username);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		Date current = new Date();
		loginHistoryPk.setLoginTime(sdf.format(current));
		loginHistory.setId(loginHistoryPk);
		loginHistory.setSuccess(success);
		loginHistory.setReason(reason);
		loginHistoryRepository.save(loginHistory);
	}

}
