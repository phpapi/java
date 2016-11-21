package com.caozj.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.vo.ProcessVo;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.util.json.JsonUtil;

/**
 * 启动流程
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processStart")
public class ProcessStartController {

  @Autowired
  private RepositoryService repositoryService;

  @RequestMapping
  public String list() {
    return "activiti/processStartList";
  }

  @RequestMapping("/listDataOfEasyUI.do")
  public String listDataOfEasyUI(ModelMap model, String processName, String sessAccount) {
    List<ProcessDefinition> list = null;
    ProcessDefinitionQuery processDefinitionQuery =
        repositoryService.createProcessDefinitionQuery();
    if (StringUtils.isNotEmpty(processName)) {
      String processNameLike = "%" + processName + "%";
      processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike(processNameLike);
    }
    list = processDefinitionQuery.active().latestVersion().list();
    List<ProcessVo> voList = new ArrayList<>(list.size());
    list.forEach((pd) -> {
      if (hasStartPermission(pd, sessAccount)) {
        ProcessVo vo = ActivitiObjectUtil.buildProcessVo(pd);
        voList.add(vo);
      }
    });
    int total = voList.size();
    String message = JsonUtil.toJson(new PageGrid(total, voList));
    model.put("message", message);
    return "message";
  }

  /**
   * 判断用户是否有启动流程的权限
   * 
   * @param pd 流程定义
   * @param account 账号
   * @return
   */
  private boolean hasStartPermission(ProcessDefinition pd, String account) {
    return true;
  }


}
