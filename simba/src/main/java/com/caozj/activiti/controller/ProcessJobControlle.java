package com.caozj.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ManagementService;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.vo.JobVo;
import com.caozj.controller.form.EasyUIPageForm;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.json.JsonUtil;

/**
 * 作业管理
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("processJob")
public class ProcessJobControlle {

  @Autowired
  private ManagementService managementService;

  @RequestMapping
  public String list() {
    return "activiti/processJobList";
  }


  @RequestMapping("/listDataOfEasyUI.do")
  public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form) {
    JobQuery query = managementService.createJobQuery().orderByJobDuedate().desc();
    List<Job> list = query.listPage((form.getPage() - 1) * form.getRows(), form.getRows());
    int total = NumberUtils.toInt(query.count() + "");
    List<JobVo> voList = new ArrayList<>(list.size());
    list.forEach((job) -> {
      JobVo vo = ActivitiObjectUtil.buildJobVo(job);
      voList.add(vo);
    });
    String message = JsonUtil.toJson(new PageGrid(total, voList));
    model.put("message", message);
    return "message";
  }

  /**
   * 执行作业
   * 
   * @param id 作业ID
   * @param model
   * @return
   */
  @RequestMapping
  public String execute(String id, ModelMap model) {
    managementService.executeJob(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 删除作业
   * 
   * @param id 作业ID
   * @param model
   * @return
   */
  @RequestMapping
  public String deleteJob(String id, ModelMap model) {
    managementService.deleteJob(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }
}
