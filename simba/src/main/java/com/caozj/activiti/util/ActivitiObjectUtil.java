package com.caozj.activiti.util;

import java.text.SimpleDateFormat;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.AttachmentEntity;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.caozj.activiti.vo.ActivityVo;
import com.caozj.activiti.vo.AttachmentVo;
import com.caozj.activiti.vo.CommentVo;
import com.caozj.activiti.vo.JobVo;
import com.caozj.activiti.vo.ProcessInstanceVo;
import com.caozj.activiti.vo.ProcessVo;
import com.caozj.activiti.vo.TaskVo;
import com.caozj.model.constant.ConstantData;

/**
 * 工作流对象工具栏
 * 
 * @author caozj
 *
 */
public class ActivitiObjectUtil {

  private static final SimpleDateFormat format = new SimpleDateFormat(ConstantData.TIME_FORMAT);

  /**
   * 从Task对象转化成Vo对象
   * 
   * @param task
   * @return
   */
  public static TaskVo buildTaskVo(Task task) {
    TaskVo vo = new TaskVo();
    vo.setId(task.getId());
    vo.setOwner(task.getOwner());
    vo.setAssignee(task.getAssignee());
    vo.setParentTaskId(task.getParentTaskId());
    vo.setName(task.getName());
    vo.setDescription(task.getDescription());
    vo.setCreateTime(format.format(task.getCreateTime()));
    vo.setProcessDefinitionId(task.getProcessDefinitionId());
    vo.setProcessInstanceId(task.getProcessInstanceId());
    return vo;
  }

  /**
   * 从历史Task对象转化成Vo对象
   * 
   * @param historyTask
   * @return
   */
  public static TaskVo buildTaskVo(HistoricTaskInstance historyTask) {
    TaskVo vo = new TaskVo();
    vo.setId(historyTask.getId());
    vo.setOwner(historyTask.getOwner());
    vo.setAssignee(historyTask.getAssignee());
    vo.setParentTaskId(historyTask.getParentTaskId());
    vo.setName(historyTask.getName());
    vo.setDescription(historyTask.getDescription());
    vo.setCreateTime(format.format(historyTask.getCreateTime()));
    vo.setStartTime(format.format(historyTask.getStartTime()));
    if (historyTask.getEndTime() != null) {
      vo.setEndTime(format.format(historyTask.getEndTime()));
    }
    vo.setProcessDefinitionId(historyTask.getProcessDefinitionId());
    vo.setProcessInstanceId(historyTask.getProcessInstanceId());
    return vo;
  }

  /**
   * 将历史活动对象转化成Vo对象
   * 
   * @param activity
   * @return
   */
  public static ActivityVo buildActivityVo(HistoricActivityInstance activity) {
    ActivityVo vo = new ActivityVo();
    vo.setActivityId(activity.getActivityId());
    vo.setActivityName(activity.getActivityName());
    vo.setActivityType(activity.getActivityType());
    vo.setAssignee(activity.getAssignee());
    vo.setDurationInMillis(activity.getDurationInMillis());
    if (activity.getEndTime() != null) {
      vo.setEndTime(format.format(activity.getEndTime()));
    }
    vo.setId(activity.getId());
    vo.setProcessInstanceId(activity.getProcessInstanceId());
    vo.setStartTime(format.format(activity.getStartTime()));
    vo.setTaskId(activity.getTaskId());
    return vo;
  }

  /**
   * 将流程对象转化成Vo对象
   * 
   * @param pd
   * @return
   */
  public static ProcessVo buildProcessVo(ProcessDefinition pd) {
    ProcessVo vo = new ProcessVo();
    vo.setId(pd.getId());
    vo.setName(pd.getName());
    vo.setKey(pd.getKey());
    vo.setDeploymentId(pd.getDeploymentId());
    vo.setDescription(pd.getDescription());
    vo.setDiagramResourceName(pd.getDiagramResourceName());
    vo.setVersion(pd.getVersion());
    vo.setCategory(pd.getCategory());
    vo.setResourceName(pd.getResourceName());
    vo.setSuspended(pd.isSuspended());
    return vo;
  }

  /**
   * 将历史流程实例对象转化成Vo对象
   * 
   * @param historicProcessInstance
   * @return
   */
  public static ProcessInstanceVo buildProcessInstanceVo(
      HistoricProcessInstance historicProcessInstance) {
    ProcessInstanceVo vo = new ProcessInstanceVo();
    vo.setBusinessKey(historicProcessInstance.getBusinessKey());
    vo.setDescription(historicProcessInstance.getDescription());
    vo.setDurationInMillis(historicProcessInstance.getDurationInMillis());
    vo.setEndTime(format.format(historicProcessInstance.getEndTime()));
    vo.setId(historicProcessInstance.getId());
    vo.setName(historicProcessInstance.getName());
    vo.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
    vo.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
    vo.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
    vo.setStartTime(format.format(historicProcessInstance.getStartTime()));
    vo.setStartUserId(historicProcessInstance.getStartUserId());
    vo.setSuperProcessInstanceId(historicProcessInstance.getSuperProcessInstanceId());
    return vo;
  }

  /**
   * 流程实例对象转化成Vo对象
   * 
   * @param processInstance
   * @return
   */
  public static ProcessInstanceVo buildProcessInstanceVo(ProcessInstance processInstance) {
    ProcessInstanceVo vo = new ProcessInstanceVo();
    vo.setBusinessKey(processInstance.getBusinessKey());
    vo.setDescription(processInstance.getDescription());
    vo.setId(processInstance.getProcessInstanceId());
    vo.setName(processInstance.getName());
    vo.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
    vo.setProcessDefinitionId(processInstance.getProcessDefinitionId());
    vo.setProcessDefinitionName(processInstance.getProcessDefinitionName());
    vo.setActivityId(processInstance.getActivityId());
    vo.setSuspended(processInstance.isSuspended());
    return vo;
  }

  /**
   * 将作业对象转化成Vo对象
   * 
   * @param job
   * @return
   */
  public static JobVo buildJobVo(Job job) {
    JobEntity j = (JobEntity) job;
    JobVo vo = new JobVo();
    vo.setDuedate(format.format(job.getDuedate()));
    vo.setExceptionMessage(job.getExceptionMessage());
    vo.setExecutionId(job.getExecutionId());
    vo.setId(job.getId());
    vo.setJobHandlerType(j.getJobHandlerConfiguration());
    vo.setJobType(j.getJobType());
    vo.setProcessDefinitionId(j.getProcessDefinitionId());
    vo.setProcessInstanceId(j.getProcessInstanceId());
    vo.setRetries(j.getRetries());
    return vo;
  }

  /**
   * 将意见对象转化成Vo对象
   * 
   * @param comment
   * @return
   */
  public static CommentVo buildCommentVo(Comment comment) {
    CommentEntity c = (CommentEntity) comment;
    CommentVo vo = new CommentVo();
    vo.setAction(c.getAction());
    vo.setFullMessage(c.getFullMessage());
    vo.setId(c.getId());
    vo.setMessage(c.getMessage());
    vo.setProcessInstanceId(c.getProcessInstanceId());
    vo.setTaskId(c.getTaskId());
    vo.setTime(format.format(c.getTime()));
    vo.setType(c.getType());
    vo.setUserId(c.getUserId());
    return vo;
  }

  /**
   * 将附件对象转化成Vo对象
   * 
   * @param attachment
   * @return
   */
  public static AttachmentVo buildAttachmentVo(Attachment attachment) {
    AttachmentEntity a = (AttachmentEntity) attachment;
    AttachmentVo vo = new AttachmentVo();
    vo.setContentId(a.getContentId());
    vo.setDescription(a.getDescription());
    vo.setId(a.getId());
    vo.setName(a.getName());
    vo.setProcessInstanceId(a.getProcessInstanceId());
    vo.setTaskId(a.getTaskId());
    vo.setTime(format.format(a.getTime()));
    vo.setType(a.getType());
    vo.setUrl(a.getUrl());
    vo.setUserId(a.getUserId());
    return vo;
  }

}
