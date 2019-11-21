package com.crm.service.login.attempt.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.LoginAttempt;
import com.crm.repository.LoginAttemptRepository;
import com.crm.service.login.attempt.LoginAttemptService;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

	@Autowired
	private LoginAttemptRepository loginAttemptRepository;

	@Transactional(noRollbackFor = Exception.class)
	@Override
	public void save(String username, int attempt) {
		// TODO Auto-generated method stub
		LoginAttempt loginAttempt = new LoginAttempt();
		loginAttempt.setUsername(username);
		loginAttempt.setAttempt(attempt);
		if (attempt == 3) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");
			Calendar date = Calendar.getInstance();
			long t = date.getTimeInMillis();
			Date add15min = new Date(t + (15 * 60000));
			loginAttempt.setAllowDate(sdf.format(add15min));
		}
		loginAttemptRepository.save(loginAttempt);
	}

	@Transactional(readOnly = true)
	@Override
	public LoginAttempt findByOne(String username) {
		// TODO Auto-generated method stub
		return loginAttemptRepository.findByOne(username); 
	}

	@Transactional(readOnly = true)
	@Override
	public void delete(String username) {
		// TODO Auto-generated method stub
		loginAttemptRepository.delete(username);
	}

	 

}
