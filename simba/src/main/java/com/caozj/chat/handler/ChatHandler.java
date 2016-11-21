package com.caozj.chat.handler;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.caozj.chat.util.ChatCache;
import com.caozj.framework.session.SessionUtil;
import com.caozj.framework.util.ApplicationContextUtil;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.model.ChatRecord;
import com.caozj.permission.model.User;
import com.caozj.service.ChatRecordService;

/**
 * 聊天处理类
 * 
 * @author caozj
 * 
 */
@Component
public class ChatHandler implements WebSocketHandler {

  private static final Log logger = LogFactory.getLog(ChatHandler.class);

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String account = getUser(session).getAccount();
    ChatCache.disconnect(account);
    logger.info("websocket connection closed......" + account);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String account = getUser(session).getAccount();
    ChatCache.connect(account, session);
    logger.info("connect to the websocket success......" + account);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    String account = getUser(session).getAccount();
    String json = message.getPayload().toString();
    logger.info("接收到" + account + "发送的消息:" + json);
    ChatRecord record = JsonUtil.toObject(json, ChatRecord.class);
    record.setSendTime(new Date());
    // TODO: 现在先用账号显示名称，以后从数据库或者缓存中取出账号对应的名称
    record.setFromName(record.getFromAccount());
    record.setToName(record.getToAccount());
    // 发送消息给双方
    WebSocketSession toSession = ChatCache.getWebSocketSession(record.getToAccount());
    if (toSession == null) {
      record.setFromName("系统提示");
      record.setContent(record.getToName() + "(" + record.getToAccount() + ")没在线");
    }
    String sendMessage = JsonUtil.toJson(record);
    session.sendMessage(new TextMessage(sendMessage));
    if (toSession != null) {
      toSession.sendMessage(new TextMessage(sendMessage));
    }
    // 将聊天记录插入到数据库中
    ChatRecordService chatRecordService =
        (ChatRecordService) ApplicationContextUtil.getBean("chatRecordService");
    chatRecordService.add(record);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
    if (session.isOpen()) {
      String account = getUser(session).getAccount();
      ChatCache.disconnect(account);
      session.close();
    }
    logger.info("websocket connection closed......");
    logger.error("连接出现异常", throwable);
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  private User getUser(WebSocketSession session) {
    return (User) session.getAttributes().get(SessionUtil.userKey);
  }

}
