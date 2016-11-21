package com.caozj.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.caozj.framework.util.file.PropertiesUtil;

/**
 * 自定义PropertyPlaceholderConfigurer返回properties内容
 * 
 * @author caozj
 * 
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static Map<String, String> ctxPropertiesMap;

	private static final String defaultConfigPropertiesFile = "/configs.properties";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static String getContextProperty(String name) {
		if (ctxPropertiesMap == null) {
			ctxPropertiesMap = PropertiesUtil.read(defaultConfigPropertiesFile);
		}
		return ctxPropertiesMap.get(name);
	}

}
