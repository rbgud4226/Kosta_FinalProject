package com.example.demo.chat.Message;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {

		// 채팅방으로 메세지 보내서 구독하고있는 클라이언트에게 메세지 전달
		config.enableSimpleBroker("/room");

		// 클라이언트에서 보낸메세지
		config.setApplicationDestinationPrefixes("/send");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/auth/ws").setAllowedOriginPatterns("*").withSockJS();
	}
}
