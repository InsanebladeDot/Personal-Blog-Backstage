package com.example.springboottext.webSocet.config;

import com.example.springboottext.webSocet.handle.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/socket").setAllowedOrigins("*");
    }

    public MyWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }
}