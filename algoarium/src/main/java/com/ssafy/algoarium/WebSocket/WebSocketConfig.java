// WebSocket 설정을 담당하는 Configuration 클래스입니다.
package com.ssafy.algoarium.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  // 이 클래스가 Spring의 Configuration 클래스임을 선언합니다.
@EnableWebSocketMessageBroker  // WebSocket 메시지 브로커를 활성화합니다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // "/topic" 경로 밑으로 데이터를 publish하면 그것들은 해당 topic에 subscribe한 클라이언트들에게 전달됩니다.
        config.setApplicationDestinationPrefixes("/app");  // "/app" prefix 경로로 들어오는 요청은 애플리케이션 내부의 Controller method가 처리합니다.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket");  // 클라이언트가 웹소켓 서버에 연결할 때 사용할 웹소켓 엔드포인트를 등록합니다. 여기서는 "/websocket" 경로를 사용하도록 설정되어 있습니다.
    }
}
