package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
