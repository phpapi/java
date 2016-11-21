package com.caozj.activiti.model;

/**
 * 流程设置
 * 
 * @author caozj
 *
 */
public class ProcessSet {

  /**
   * 流程id
   */
  private String id;

  /**
   * 发送类型 singleOne:在发送的时候，必须选择一个人签收任务，然后系统自动签收
   * autoSend:在发送的时候，自动发送给所有有权限的人，等收到任务的人保存或者发送的时候，才签收任务，只要有一个人签收任务之后，其他人就看不到了
   */
  private String sendType;

  /**
   * 是否所有人员都可以启动流程
   */
  private int allStart;

  public int getAllStart() {
    return allStart;
  }

  public void setAllStart(int allStart) {
    this.allStart = allStart;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSendType() {
    return sendType;
  }

  public void setSendType(String sendType) {
    this.sendType = sendType;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProcessSet [id=");
    builder.append(id);
    builder.append(", sendType=");
    builder.append(sendType);
    builder.append("]");
    return builder.toString();
  }



}
