package com.crm.config.webSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketManager {
	private static ConcurrentHashMap<String, WebSocketSession> manager = new ConcurrentHashMap<String, WebSocketSession>();
	
	public static void add(String key, WebSocketSession webSocketSession) {
		manager.put(key, webSocketSession);
		for (Map.Entry<String, WebSocketSession> index : manager.entrySet()) {
			log.info("manager   " + index.getValue());
		}
	}

	public static void remove(String key) {
		manager.remove(key);
	}

	public static WebSocketSession get(String key) {
		return manager.get(key);
	}
	
}
