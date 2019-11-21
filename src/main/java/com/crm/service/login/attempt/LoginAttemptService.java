package com.crm.service.login.attempt;

import com.crm.model.entity.LoginAttempt;

public interface LoginAttemptService {
	public void save(String username, int attempt);
	public LoginAttempt findByOne(String username);
	public void delete(String username);
}
