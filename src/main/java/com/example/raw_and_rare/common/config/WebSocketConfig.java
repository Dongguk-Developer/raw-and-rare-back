package com.example.raw_and_rare.common.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // WebSocket 연결 주소
                .setAllowedOriginPatterns("*")
                .withSockJS();       // SockJS fallback 지원
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/message");  // 메세지 수신
        config.setApplicationDestinationPrefixes("/app");  // 메세지 송신 prefix
    }
}
