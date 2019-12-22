package com.crm.service.notification.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.Notification;
import com.crm.repository.NotificationRepository;
import com.crm.service.notification.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(Notification notification) throws Exception{
		// TODO Auto-generated method stub
		try {
			notificationRepository.save(notification);
		}catch(Exception e) {
			throw new Exception("新增失敗!",e);
		}
		
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Notification> all(int page) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page, 10, new Sort(Direction.DESC, "id"));
		return notificationRepository.findAll(pageable);
	}

}
