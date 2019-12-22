package com.crm.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageBroker {

	@Autowired
	private SimpMessagingTemplate template;
	
	public void broadcast(String title , String bulletin) {
		log.info("broadcast");
		//Map<String,String> map = Map.of("title", title,"bulletin",bulletin);
		template.convertAndSend("/topic/bulletin", 123);
	}
}
