package com.caozj.framework.session;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.caozj.common.CustomizedPropertyPlaceholderConfigurer;
import com.caozj.service.impl.RedisByteCacheServiceImpl;

@Component("sessionCache")
public class SessionCacheSessionImpl extends RedisByteCacheServiceImpl {

	@PostConstruct
	public void init() {
		setRedisHost(CustomizedPropertyPlaceholderConfigurer.getContextProperty("distributed.redis.host"));
		setRedisPort(NumberUtils.toInt(CustomizedPropertyPlaceholderConfigurer.getContextProperty("distributed.redis.port"), 6379));
		setRedisPassword(CustomizedPropertyPlaceholderConfigurer.getContextProperty("distributed.redis.password"));
		super.init();
	}

}
