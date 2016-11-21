package com.caozj.activiti.service.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caozj.activiti.service.ProcessService;

/**
 * 流程Service实现类
 * 
 * @author caozj
 *
 */
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private FormService formService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private IdentityService identityService;

  @Override
  public void batchDeleteProcess(List<String> processIDs) {
    processIDs.forEach((processID) -> {
      this.deleteProcess(processID);
    });
  }

  @Override
  public void deleteProcess(String processID) {
    ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(processID).singleResult();
    if (pd == null) {
      return;
    }
    repositoryService.deleteDeployment(pd.getDeploymentId(), true);
  }

  @Override
  public Task saveStartProcess(String processID, Map<String, String> params, String account) {
    identityService.setAuthenticatedUserId(account);
    ProcessInstance processInstance = formService.submitStartFormData(processID, params);
    Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.claim(task.getId(), account);
    return task;
  }

  @Override
  public void submitStartProcess(String processID, Map<String, String> params, String account) {
    Task task = this.saveStartProcess(processID, params, account);
    formService.submitTaskFormData(task.getId(), params);
  }

  @Override
  public void saveTask(Task task, Map<String, String> params, String account) {
    if (StringUtils.isEmpty(task.getAssignee())) {
      taskService.claim(task.getId(), account);
    }
    formService.saveFormData(task.getId(), params);
  }

  @Override
  public void submitTask(Task task, Map<String, String> params, String account) {
    if (StringUtils.isEmpty(task.getAssignee())) {
      taskService.claim(task.getId(), account);
    }
    formService.submitTaskFormData(task.getId(), params);
  }

}
