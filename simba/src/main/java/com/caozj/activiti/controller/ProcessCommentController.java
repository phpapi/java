package com.caozj.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.activiti.form.CommentForm;
import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.vo.CommentVo;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.permission.service.UserService;

/**
 * 流程意见Controller
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processComment")
public class ProcessCommentController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private IdentityService identityService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  private UserService userService;

  /**
   * 提交意见
   * 
   * @param model
   * @param sessAccount
   * @param commentForm
   * @return
   */
  @RequestMapping
  public String add(ModelMap model, String sessAccount, CommentForm commentForm) {
    identityService.setAuthenticatedUserId(sessAccount);
    taskService.addComment(commentForm.getTaskId(), commentForm.getProcessInstanceId(),
        commentForm.getContent());
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 删除意见
   * 
   * @param id 意见ID
   * @param model
   * @return
   */
  @RequestMapping
  public String delete(String id, ModelMap model) {
    taskService.deleteComment(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 列出意见列表
   * 
   * @param model
   * @param processInstanceId 流程实例ID
   * @return
   */
  @RequestMapping
  public String list(ModelMap model, String processInstanceId) {
    List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
    Map<String, String> taskMap = new HashMap<>();
    List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery()
        .processInstanceId(processInstanceId).list();
    taskList.forEach((task) -> {
      taskMap.put(task.getId(), task.getName());
    });
    int total = list.size();
    List<CommentVo> voList = new ArrayList<>(total);
    list.forEach((c) -> {
      CommentVo vo = ActivitiObjectUtil.buildCommentVo(c);
      if ("AddComment".equals(vo.getAction())) {
        vo.setUserName(userService.getDesc(vo.getUserId()));
        vo.setTaskName(taskMap.get(vo.getTaskId()));
        voList.add(vo);
      }
    });
    String message = JsonUtil.toJson(new PageGrid(total, voList));
    model.put("message", message);
    return "message";
  }

}
