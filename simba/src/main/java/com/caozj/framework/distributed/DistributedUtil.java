package com.caozj.framework.distributed;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.caozj.framework.util.common.SerializeUtil;

/**
 * 分布式工具类
 * 
 * @author caozj
 *
 */
@Component
public class DistributedUtil {

  private static final Log logger = LogFactory.getLog(DistributedUtil.class);

  @Autowired
  private DistributedInterface di;

  @Value("${distributed.channel}")
  private String channel;

  @Value("${session.isEnableDistributedSession}")
  private String distributedEnable;

  private byte[] key;

  @PostConstruct
  private void init() {
    if (!"true".equalsIgnoreCase(distributedEnable)) {
      logger.info("没有打开分布式开关session.isEnableDistributedSession，不能使用分布式功能");
      return;
    }
    key = SerializeUtil.serialize(channel);
    // 订阅频道
    di.subscribe(key, new RedisReceiveMessage());
    logger.info("订阅集群频道成功");
  }

  /**
   * 发送一个消息对象给集群中的所有服务器,通知所有服务器执行方法
   * 
   * @param message 消息对象
   */
  public void executeInCluster(ClusterMessage message) {
    if (!"true".equalsIgnoreCase(distributedEnable)) {
      logger.error("没有打开分布式开关session.isEnableDistributedSession，不能使用分布式功能");
      return;
    }
    di.publish(key, SerializeUtil.serialize(message));
  }

}
