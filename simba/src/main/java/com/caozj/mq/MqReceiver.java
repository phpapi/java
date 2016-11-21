package com.caozj.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * mq的接收端
 * 
 * @author caozj
 *
 */
@Component("mqReceiver")
public class MqReceiver implements MessageListener {

	private static final Log logger = LogFactory.getLog(MqReceiver.class);

	@Override
	public void onMessage(Message message) {
		logger.info(message.toString());
	}

}
