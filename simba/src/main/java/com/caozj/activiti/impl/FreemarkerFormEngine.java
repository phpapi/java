package com.caozj.activiti.impl;

import java.util.HashMap;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.FormEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caozj.activiti.util.FreemarkerRenderFormUtil;
import com.caozj.framework.util.ApplicationContextUtil;
import com.caozj.model.constant.ConstantData;

/**
 * freemarker表单引擎
 * 
 * @author caozj
 *
 */
public class FreemarkerFormEngine implements FormEngine {

  private static final Log logger = LogFactory.getLog(FreemarkerFormEngine.class);


  @Override
  public String getName() {
    return ConstantData.FREEMARKER_FORMENGINE;
  }

  @Override
  public Object renderStartForm(StartFormData startForm) {
    try {
      RepositoryService repositoryService =
          (RepositoryService) ApplicationContextUtil.getBean("repositoryService");
      return FreemarkerRenderFormUtil.renderForm(repositoryService, new HashMap<String, Object>(),
          startForm.getFormKey(), startForm.getDeploymentId());
    } catch (Exception e) {
      logger.error("freemark渲染表单异常", e);
    }
    return null;
  }

  @Override
  public Object renderTaskForm(TaskFormData taskForm) {
    try {
      TaskService taskService = (TaskService) ApplicationContextUtil.getBean("taskService");
      RepositoryService repositoryService =
          (RepositoryService) ApplicationContextUtil.getBean("repositoryService");
      return FreemarkerRenderFormUtil.renderForm(repositoryService,
          taskService.getVariables(taskForm.getTask().getId()), taskForm.getFormKey(),
          taskForm.getDeploymentId());
    } catch (Exception e) {
      logger.error("freemark渲染表单异常", e);
    }
    return null;
  }

}
