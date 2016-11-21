package com.caozj.chat.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.caozj.chat.handler.ChatHandler;
import com.caozj.chat.interceptor.ChatInterceptor;

/**
 * websocket配置类
 * 
 * @author caozj
 * 
 */
@Configuration
@EnableWebSocket
public class ChatConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

  private static final Log logger = LogFactory.getLog(ChatConfig.class);

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(chatHandler(), "/websck").addInterceptors(new ChatInterceptor());
    registry.addHandler(chatHandler(), "/sockjs/websck").addInterceptors(new ChatInterceptor())
        .withSockJS();
    logger.info("web socket registry success");
  }

  @Bean
  public ChatHandler chatHandler() {
    return new ChatHandler();
  }

}
