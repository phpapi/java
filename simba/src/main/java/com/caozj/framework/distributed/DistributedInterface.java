package com.caozj.framework.distributed;

/**
 * 分布式发布订阅接口
 * 
 * @author caozj
 * 
 *
 */
public interface DistributedInterface {

  /**
   * 发布消息
   * 
   * @param channel 频道
   * @param message 消息内容
   */
  void publish(byte[] channel, byte[] message);

  /**
   * 订阅消息
   * 
   * @param channel 频道
   * @param receiveMessage 消息处理类
   */
  void subscribe(byte[] channel, ReceiveMessageInterface receiveMessage);
}
