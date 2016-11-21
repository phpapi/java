package com.caozj.cache;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

/**
 * 自定义的key生成策略(Spring Cache使用,针对方法级别的缓存)
 * 
 * @author caozj
 *
 */
@Component
public class CustomKeyGenerator implements KeyGenerator {

	private static final Log logger = LogFactory.getLog(CustomKeyGenerator.class);

	@Override
	public Object generate(Object target, Method method, Object... params) {
		StringBuilder key = new StringBuilder();
		key.append(target.getClass().getName()).append("-").append(method.getName()).append("-");
		if (params != null && params.length > 0) {
			for (Object param : params) {
				key.append(param.toString()).append("-");
			}
		}
		String result = key.toString();
		logger.info("key========>" + result);
		return result;
	}

}
