package com.caozj.chat.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

/**
 * 聊天缓存类
 * 
 * @author caozj
 * 
 */
public class ChatCache {

  private static final Map<String, WebSocketSession> accountSessionMap =
      new HashMap<String, WebSocketSession>();

  /**
   * websocket连接成功
   * 
   * @param account
   * @param session
   */
  public static void connect(String account, WebSocketSession session) {
    accountSessionMap.put(account, session);
  }

  /**
   * webservice关闭连接
   * 
   * @param account
   */
  public static void disconnect(String account) {
    accountSessionMap.remove(account);
  }

  /**
   * 获取同时在线人数
   * 
   * @return
   */
  public static int countOnline() {
    return accountSessionMap.size();
  }

  /**
   * 获取账号对应的session
   * 
   * @param account
   * @return
   */
  public static WebSocketSession getWebSocketSession(String account) {
    return accountSessionMap.get(account);
  }

}
