package com.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

	@KafkaListener(topics = "kafkatopic", groupId="uniquegroupId")
	void listener(String data) {
		System.out.println("Received data from Producer:" + data);
	}
}
