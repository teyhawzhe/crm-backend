package com.crm.service.notification;

import org.springframework.data.domain.Page;

import com.crm.model.entity.Notification;

public interface NotificationService {
	public void save(Notification notification) throws Exception;
	public Page<Notification> all(int page);
}
