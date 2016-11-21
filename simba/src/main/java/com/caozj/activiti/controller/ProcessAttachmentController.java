package com.caozj.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.restlet.engine.io.IoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.vo.AttachmentVo;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.file.FileUtils;
import com.caozj.framework.util.json.JsonUtil;
import com.caozj.permission.service.UserService;

/**
 * 流程附件Controller
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processAttachment")
public class ProcessAttachmentController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private IdentityService identityService;

  @Autowired
  private UserService userService;

  /**
   * 获取所有的附件信息
   * 
   * @param model
   * @param processInstanceId 流程实例ID
   * @return
   */
  @RequestMapping
  public String list(ModelMap model, String processInstanceId) {
    List<Attachment> list = taskService.getProcessInstanceAttachments(processInstanceId);
    int total = list.size();
    List<AttachmentVo> voList = new ArrayList<>(list.size());
    list.forEach((attachment) -> {
      AttachmentVo vo = ActivitiObjectUtil.buildAttachmentVo(attachment);
      vo.setUserName(userService.getDesc(vo.getUserId()));
      voList.add(vo);
    });
    String message = JsonUtil.toJson(new PageGrid(total, voList));
    model.put("message", message);
    return "message";
  }

  /**
   * 新增流程附件
   * 
   * @param model
   * @param file
   * @param attachmentTaskId 任务ID
   * @param attachmentProcessInstanceId 流程实例ID
   * @param sessAccount
   * @return
   * @throws IOException
   */
  @RequestMapping
  public String add(ModelMap model, MultipartFile file, String attachmentTaskId, String attachmentProcessInstanceId,
      String sessAccount) throws IOException {
    String fileName = file.getOriginalFilename();
    String fileExt = FileUtils.getFileExt(fileName).substring(1);
    String attachmentType = file.getContentType() + ";" + fileExt;
    identityService.setAuthenticatedUserId(sessAccount);
    taskService.createAttachment(attachmentType, attachmentTaskId, attachmentProcessInstanceId, fileName, fileName,
        file.getInputStream());
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 删除流程附件
   * 
   * @param id 附件id
   * @param model
   * @return
   */
  @RequestMapping
  public String delete(String id, ModelMap model) {
    taskService.deleteAttachment(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 下载附件
   * 
   * @param id 附件id
   * @param response
   * @throws IOException
   */
  @RequestMapping
  public void download(String id, HttpServletResponse response) throws IOException {
    Attachment attachment = taskService.getAttachment(id);
    InputStream content = taskService.getAttachmentContent(id);
    String contentType = attachment.getType().split(";")[0];
    response.addHeader("Content-Type", contentType + ";charset=UTF-8");
    String fileName = attachment.getName();
    response.setHeader("Content-Disposition", "attachement;filename=" + new String(fileName.getBytes(),"ISO8859-1"));
    IoUtils.copy(content, response.getOutputStream());
  }
}
