package com.caozj.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoJobs {

	private Log logger = LogFactory.getLog(this.getClass());

	@Scheduled(cron="0 0/1 * * * *")
	private void initJobs() {
		logger.info(this.getClass() + ":executed");
	}
}
