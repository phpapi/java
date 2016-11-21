package com.caozj.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

/**
 * Spring上下文工具类
 * 
 * @author caozj
 *
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

  private static ApplicationContext context;

  private static Log logger = LogFactory.getLog(ApplicationContextUtil.class);

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    ApplicationContextUtil.context = context;
    logger.info("init ApplicationContext successfully");
  }

  public static ApplicationContext getContext() {
    return context;
  }

  public static Object getBean(String beanName) {
    Object object = null;
    try {
      object = context.getBean(beanName);
    } catch (NullPointerException e) {
      logger.warn("Spring容器中找不到beanName:" + beanName);
    }
    return object;
  }

  /**
   * 取Bean实例
   * 
   * @param <T>
   * @param clazz
   * @return
   */
  public static <T> T getBean(Class<T> clazz) {
    T object = null;
    try {
      object = getContext().getBean(clazz);
    } catch (NullPointerException e) {
      logger.warn("Spring容器中找不到class:" + clazz.getName());
    }
    return object;
  }

  /**
   * 动态注入Bean
   * 
   * @param beanName
   * @param beanClass
   * @param properties
   */
  public static void registerBean(String beanName, Class<?> beanClass,
      Map<String, String> properties) {
    registerBean(beanName, beanClass, properties, null);
  }

  /**
   * 动态注入Bean
   * 
   * @param beanName
   * @param beanClass
   * @param properties
   * @param refs
   */
  public static void registerBean(String beanName, Class<?> beanClass,
      Map<String, String> properties, Map<String, String> refs) {
    BeanDefinitionBuilder userBeanDefinitionBuilder =
        BeanDefinitionBuilder.genericBeanDefinition(beanClass);
    if (properties != null && properties.size() > 0) {
      for (Map.Entry<String, String> entry : properties.entrySet()) {
        userBeanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue());
      }
    }
    if (refs != null && refs.size() > 0) {
      for (Map.Entry<String, String> entry : refs.entrySet()) {
        userBeanDefinitionBuilder.addPropertyReference(entry.getKey(), entry.getValue());
      }
    }
    DefaultListableBeanFactory defaultListableBeanFactory = getBeanFactory();
    removeBean(beanName);
    defaultListableBeanFactory.registerBeanDefinition(beanName,
        userBeanDefinitionBuilder.getRawBeanDefinition());
  }

  /**
   * 动态删除Bean
   * 
   * @param beanName
   */
  public static void removeBean(String beanName) {
    DefaultListableBeanFactory defaultListableBeanFactory = getBeanFactory();
    if (defaultListableBeanFactory.containsBean(beanName)) {
      defaultListableBeanFactory.removeBeanDefinition(beanName);
    }
  }

  /**
   * 获取一个类型的所有的Bean对象列表
   * 
   * @param type 类型
   * @return
   * @throws BeansException
   */
  public static <T> List<T> getBeansOfType(Class<T> type) throws BeansException {
    List<T> beans = new ArrayList<T>(getContext().getBeansOfType(type).values());
    AnnotationAwareOrderComparator.sort(beans);
    return beans;
  }

  /**
   * 发布一个事件，所有的实现了ApplicationListener的类都会接收到这个事件
   * 
   * @param event 事件
   */
  public static void publishEvent(ApplicationEvent event) {
    getContext().publishEvent(event);
  }

  /**
   * 获取bean工厂
   * 
   * @return
   */
  private static DefaultListableBeanFactory getBeanFactory() {
    ConfigurableApplicationContext configurableApplicationContext =
        (ConfigurableApplicationContext) context;
    DefaultListableBeanFactory defaultListableBeanFactory =
        (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
    return defaultListableBeanFactory;
  }

}
