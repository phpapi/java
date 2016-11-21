package com.caozj.framework.util.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.caozj.activiti.interfaces.SendUser;
import com.caozj.activiti.util.SendUserData;
import com.caozj.framework.distributed.ClusterExecute;
import com.caozj.framework.distributed.DistributedData;
import com.caozj.framework.session.page.PageParameter;
import com.caozj.framework.session.page.PageParameterUtil;

/**
 * 所有的bean初始化时执行的操作
 * 
 * @author caozj
 *
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

  private static final Log logger = LogFactory.getLog(CustomBeanPostProcessor.class);

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof PageParameter) {
      PageParameterUtil.registerPageParameter((PageParameter) bean);
      logger.info("注入页面扩展参数:" + ((PageParameter) bean).getKey());
    } else if (bean instanceof ClusterExecute) {
      String classFullPath = bean.getClass().getCanonicalName();
      DistributedData.getInstance().add(classFullPath, (ClusterExecute) bean);
      logger.info("注入集群方法:" + classFullPath);
    } else if (bean instanceof SendUser) {
      SendUser sendUser = (SendUser) bean;
      SendUserData.getInstance().add(sendUser.getProcessKey(), sendUser);
      logger.info("注入工作流发送者流程:" + sendUser.getProcessKey());
    }
    return bean;
  }

}
