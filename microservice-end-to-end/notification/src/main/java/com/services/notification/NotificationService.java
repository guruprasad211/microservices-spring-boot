package com.services.notification;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {

	public Boolean send(NotificationRequest notificationRequest) {
		log.info("Notification sent {}",notificationRequest);
		return true;
	}

}
