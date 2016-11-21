package com.caozj.activiti.vo;

/**
 * 任务Vo
 * 
 * @author caozj
 *
 */
public class TaskVo {

  private String id;
  private String owner;
  private String assignee;
  private String assigneeName;
  private String parentTaskId;
  private String name;
  private String description;
  private String createTime;
  private String processInstanceId;
  private String processDefinitionId;
  private String startTime;
  private String endTime;
  private String title;

  public String getAssigneeName() {
    return assigneeName;
  }

  public void setAssigneeName(String assigneeName) {
    this.assigneeName = assigneeName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getParentTaskId() {
    return parentTaskId;
  }

  public void setParentTaskId(String parentTaskId) {
    this.parentTaskId = parentTaskId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TaskVo [owner=");
    builder.append(owner);
    builder.append(", assignee=");
    builder.append(assignee);
    builder.append(", parentTaskId=");
    builder.append(parentTaskId);
    builder.append(", name=");
    builder.append(name);
    builder.append(", description=");
    builder.append(description);
    builder.append(", createTime=");
    builder.append(createTime);
    builder.append(", processInstanceId=");
    builder.append(processInstanceId);
    builder.append(", processDefinitionId=");
    builder.append(processDefinitionId);
    builder.append("]");
    return builder.toString();
  }


}
