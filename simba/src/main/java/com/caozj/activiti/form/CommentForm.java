package com.caozj.activiti.form;

/**
 * 意见Form
 * 
 * @author caozj
 *
 */
public class CommentForm {

  private String processInstanceId;

  private String taskId;

  private String content;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


}
