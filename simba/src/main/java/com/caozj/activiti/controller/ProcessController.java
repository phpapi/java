package com.caozj.activiti.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.activiti.service.ProcessService;
import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.util.FreemarkerRenderFormUtil;
import com.caozj.activiti.vo.TaskVo;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.common.StringUtil;
import com.caozj.model.constant.ConstantData;
import com.caozj.permission.service.UserService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 流程操作的Controller
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/process")
public class ProcessController {

  private static final Log logger = LogFactory.getLog(ProcessController.class);

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private FormService formService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private ProcessService processService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  private UserService userService;

  /**
   * 启动流程页面
   * 
   * @param id 流程ID
   * @param model
   * @return
   */
  @RequestMapping
  public String start(String id, ModelMap model, String sessAccount, String sessName) {
    ProcessDefinition pd =
        repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
    if (pd == null) {
      logger.error("流程不存在:" + id);
      return "redirect:/processStart/list.do";
    }
    Object startForm = formService.getRenderedStartForm(id, ConstantData.FREEMARKER_FORMENGINE);
    model.put("pd", pd);
    model.put("startForm", startForm);
    model.put("startUser", sessAccount);
    model.put(ConstantData.START_USERNAME, sessName);
    return "activiti/startForm";
  }

  /**
   * 进入办理任务页面
   * 
   * @param id 任务ID
   * @param model
   * @param sessAccount
   * @return
   */
  @RequestMapping
  public String taskForm(String id, ModelMap model) {
    Task task = taskService.createTaskQuery().taskId(id).singleResult();
    if (task == null) {
      logger.error("任务已经不存在:" + id);
      return "redirect:/processDoing/list.do";
    }
    ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(task.getProcessDefinitionId()).singleResult();
    Object taskForm = formService.getRenderedTaskForm(id, ConstantData.FREEMARKER_FORMENGINE);
    Object startUserName = taskService.getVariable(id, ConstantData.START_USERNAME);
    model.put("taskForm", taskForm);
    model.put("pd", pd);
    model.put("task", task);
    model.put("assigneeName", userService.getDesc(task.getAssignee()));
    model.put(ConstantData.START_USERNAME, startUserName);
    return "activiti/taskForm";
  }

  /**
   * 查看任务页面
   * 
   * @param id 任务ID
   * @param model
   * @return
   * @throws IOException
   * @throws TemplateException
   */
  @RequestMapping
  public String viewTaskForm(String id, ModelMap model, String type)
      throws IOException, TemplateException {
    HistoricTaskInstance historyTask =
        historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
    Task task = null;
    if (historyTask == null) {
      task = taskService.createTaskQuery().taskId(id).singleResult();
    }
    if (task == null && historyTask == null) {
      logger.error("任务已经不存在:" + id);
      return "redirect:/processDone/list.do";
    }
    TaskVo vo = null;
    if (historyTask != null) {
      vo = ActivitiObjectUtil.buildTaskVo(historyTask);
    } else {
      vo = ActivitiObjectUtil.buildTaskVo(task);
    }
    ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(vo.getProcessDefinitionId()).singleResult();
    Object taskForm = null;
    Object startUserName = null;
    if (task != null) {
      // 待办任务渲染表单
      taskForm = formService.getRenderedTaskForm(id, ConstantData.FREEMARKER_FORMENGINE);
      startUserName = taskService.getVariable(id, ConstantData.START_USERNAME);
    } else {
      // 已办任务渲染表单
      List<HistoricVariableInstance> vList = historyService.createHistoricVariableInstanceQuery()
          .processInstanceId(vo.getProcessInstanceId()).list();
      Map<String, Object> properties = new HashMap<>(vList.size());
      vList.forEach((v) -> {
        properties.put(v.getVariableName(), v.getValue());
      });
      startUserName = properties.get(ConstantData.START_USERNAME);
      taskForm = FreemarkerRenderFormUtil.renderForm(repositoryService, properties,
          historyTask.getFormKey(), pd.getDeploymentId());
    }
    model.put("taskForm", taskForm);
    model.put("pd", pd);
    model.put("task", vo);
    model.put("assigneeName", userService.getDesc(vo.getAssignee()));
    model.put(ConstantData.START_USERNAME, startUserName);
    model.put("type", type);
    return "activiti/viewTaskForm";
  }

  /**
   * 查看流程表单
   * 
   * @param id 流程实例ID
   * @param model
   * @param type
   * @return
   * @throws TemplateException
   * @throws IOException
   * @throws ParseException
   * @throws MalformedTemplateNameException
   * @throws TemplateNotFoundException
   */
  @RequestMapping
  public String viewProcessForm(String id, ModelMap model, String type)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
      TemplateException {
    HistoricProcessInstance historicProcessInstance =
        historyService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
    HistoricVariableInstance variableInstance = historyService.createHistoricVariableInstanceQuery()
        .processInstanceId(id).variableName(ConstantData.START_USERNAME).singleResult();
    Object startUserName = variableInstance.getValue();
    Task task = null;
    Object taskForm = null;
    List<Task> taskList = taskService.createTaskQuery().processInstanceId(id).active()
        .orderByTaskCreateTime().desc().list();
    if (taskList != null && taskList.size() > 0) {
      task = taskList.get(0);
    }
    if (task != null) {
      // 待办任务渲染表单
      taskForm = formService.getRenderedTaskForm(task.getId(), ConstantData.FREEMARKER_FORMENGINE);
    } else {
      // 已办任务渲染表单
      List<HistoricVariableInstance> vList =
          historyService.createHistoricVariableInstanceQuery().processInstanceId(id).list();
      Map<String, Object> properties = new HashMap<>(vList.size());
      vList.forEach((v) -> {
        properties.put(v.getVariableName(), v.getValue());
      });
      List<HistoricTaskInstance> historyTaskList =
          historyService.createHistoricTaskInstanceQuery().processInstanceId(id).list();
      if (historyTaskList != null && historyTaskList.size() > 0) {
        taskForm = FreemarkerRenderFormUtil.renderForm(repositoryService, properties,
            historyTaskList.get(0).getFormKey(), pd.getDeploymentId());
      }
    }
    model.put("taskForm", taskForm);
    model.put("id", id);
    model.put("type", type);
    model.put(ConstantData.START_USERNAME, startUserName);
    model.put("pd", pd);
    return "activiti/viewProcessForm";
  }

  /**
   * 保存启动流程(启动流程，自己签收任务，可以在自己的待办任务查询到，后接着执行)
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping
  public String saveStart(ModelMap model, HttpServletRequest request, String sessAccount) {
    Map<String, String> params = buildParam(request);
    processService.saveStartProcess(params.get("processDefinitionId"), params, sessAccount);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  private Map<String, String> buildParam(HttpServletRequest request) {
    Map<String, String> params = new HashMap<>();
    request.getParameterMap().forEach((key, value) -> {
      if (value.length == 1) {
        params.put(key, value[0]);
      } else {
        params.put(key, StringUtil.join(value, ","));
      }
    });
    return params;
  }

  /**
   * 提交启动流程(启动流程，自己签收任务，完成任务到下一个活动，可以在自己的已办任务中查询到)
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping
  public String submitStart(ModelMap model, HttpServletRequest request, String sessAccount) {
    Map<String, String> params = buildParam(request);
    processService.submitStartProcess(params.get("processDefinitionId"), params, sessAccount);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 保存任务
   * 
   * @param model
   * @param request
   * @param sessAccount
   * @return
   */
  @RequestMapping
  public String saveTask(ModelMap model, HttpServletRequest request, String sessAccount) {
    Map<String, String> params = buildParam(request);
    Task task = taskService.createTaskQuery().taskId(params.get("taskId")).singleResult();
    if (task == null) {
      logger.error("任务已经不存在:" + params.get("taskId"));
      return "redirect:/processDoing/list.do";
    }
    processService.saveTask(task, params, sessAccount);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 提交任务
   * 
   * @param model
   * @param request
   * @param sessAccount
   * @return
   */
  @RequestMapping
  public String submitTask(ModelMap model, HttpServletRequest request, String sessAccount) {
    Map<String, String> params = buildParam(request);
    Task task = taskService.createTaskQuery().taskId(params.get("taskId")).singleResult();
    if (task == null) {
      logger.error("任务已经不存在:" + params.get("taskId"));
      return "redirect:/processDoing/list.do";
    }
    processService.submitTask(task, params, sessAccount);
    model.put("message", new JsonResult().toJson());
    return "message";
  }
}
