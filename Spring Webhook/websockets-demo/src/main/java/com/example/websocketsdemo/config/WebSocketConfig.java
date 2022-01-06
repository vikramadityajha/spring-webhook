package com.example.websocketsdemo.config;

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
		//config.enableSimpleBroker("/topic");
		config.setUserDestinationPrefix("/secured/user");
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/secured/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// with sockjs
		registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*").withSockJS();
		//with user
//		registry.addEndpoint("/ws-message").setHandshakeHandler(new CustomHandshakeHandler())
//		.setAllowedOriginPatterns("*").withSockJS();
		// without sockjs
		//registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*");
	}
}
