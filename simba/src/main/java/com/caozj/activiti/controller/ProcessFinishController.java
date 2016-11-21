package com.caozj.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.vo.ProcessInstanceVo;
import com.caozj.controller.form.EasyUIPageForm;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.model.constant.ConstantData;

/**
 * 已归档流程
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processFinish")
public class ProcessFinishController {

  @Autowired
  private HistoryService historyService;

  @RequestMapping
  public String list() {
    return "activiti/processFinishList";
  }

  @RequestMapping("/listDataOfEasyUI.do")
  public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form, String processName) {
    HistoricProcessInstanceQuery query =
        historyService.createHistoricProcessInstanceQuery().finished();
    if (StringUtils.isNoneEmpty(processName)) {
      query.processDefinitionName(processName);
    }
    List<HistoricProcessInstance> list = query.orderByProcessInstanceEndTime().desc()
        .listPage((form.getPage() - 1) * form.getRows(), form.getRows());
    int total = NumberUtils.toInt(query.count() + "");
    List<ProcessInstanceVo> voList = new ArrayList<>(list.size());
    list.forEach((historicProcessInstance) -> {
      ProcessInstanceVo vo = ActivitiObjectUtil.buildProcessInstanceVo(historicProcessInstance);
      HistoricVariableInstance variableInstance = historyService
          .createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId())
          .variableName(ConstantData.TITLE).singleResult();
      if (variableInstance != null) {
        vo.setTitle(variableInstance.getValue().toString());
      }
      voList.add(vo);
    });
    String message = JsonUtil.toJson(new PageGrid(total, voList));
    model.put("message", message);
    return "message";
  }

  /**
   * 删除流程实例
   * 
   * @param id 流程实例ID
   * @param model
   * @return
   */
  @RequestMapping
  public String deleteProcessInstance(String id, ModelMap model) {
    historyService.deleteHistoricProcessInstance(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }
}
