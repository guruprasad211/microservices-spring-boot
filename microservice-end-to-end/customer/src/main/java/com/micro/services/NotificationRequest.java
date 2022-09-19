package com.micro.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NotificationRequest {
	private Integer id;
	private Integer custId;
	private String message;

}
