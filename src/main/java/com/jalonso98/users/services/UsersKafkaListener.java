package com.jalonso98.users.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UsersKafkaListener {
	
	private static final Logger log = LoggerFactory.getLogger(UsersKafkaListener.class);

	@KafkaListener(topics ="users-app", groupId ="kafkaGroup")
	public void listen(String message) {
	log.info("Messasge Received: {}", message);
	}
	
	
}
