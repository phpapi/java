package com.caozj.mq;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mq发送端
 * 
 * @author caozj
 *
 */
@Component
public class MqSender {

	private static final Log logger = LogFactory.getLog(MqSender.class);
	
	@Value("${mq.key}")
	private String key;

//	@Resource
	private AmqpTemplate amqpTemplate;

	public void sendMessage(Object message) { 
		amqpTemplate.convertAndSend(key,message);
		logger.info("send message:" + message.toString());
	}

}
