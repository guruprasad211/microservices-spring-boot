package com.services.notification;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
public class NotificationController {
	
	private NotificationService notificationService;
	
	@PostMapping
	public Boolean sendNotification(@RequestBody NotificationRequest notificationRequest) {
		log.info("In sendNotification {}", notificationRequest);
		
		return notificationService.send(notificationRequest);
		
	}
}
