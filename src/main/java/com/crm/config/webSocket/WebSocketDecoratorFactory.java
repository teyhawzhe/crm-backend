package com.crm.config.webSocket;

import java.security.Principal;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketDecoratorFactory implements WebSocketHandlerDecoratorFactory {

	@Override
	public WebSocketHandler decorate(WebSocketHandler handler) {
		// TODO Auto-generated method stub
		return new WebSocketHandlerDecorator(handler) {

			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				// TODO Auto-generated method stub
				log.info("有人連接了", session.getId());
				Principal principal = session.getPrincipal();
				if(null!=principal) {
					SocketManager.add(principal.getName(), session);
				}
				super.afterConnectionEstablished(session);
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				// TODO Auto-generated method stub
				log.info("有人離開了", session.getId());
				Principal principal = session.getPrincipal();
				if (principal != null) {
					SocketManager.remove(principal.getName());
				}
				super.afterConnectionClosed(session, closeStatus);
			}
			
		};
	}

}
