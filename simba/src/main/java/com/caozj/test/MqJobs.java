package com.caozj.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.caozj.mq.MqSender;

@Component
public class MqJobs {

	@Autowired
	private MqSender mqSender;

//	@Scheduled(cron = "1/10 0/1 * * * *")
	private void initJobs() {
		mqSender.sendMessage(new Date().toString());
	}
}
