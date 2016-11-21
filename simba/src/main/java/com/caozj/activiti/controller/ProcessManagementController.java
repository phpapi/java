package com.caozj.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.caozj.activiti.service.ProcessService;
import com.caozj.activiti.util.ActivitiObjectUtil;
import com.caozj.activiti.vo.ProcessVo;
import com.caozj.controller.form.EasyUIPageForm;
import com.caozj.framework.model.easyui.PageGrid;
import com.caozj.framework.model.json.JsonResult;
import com.caozj.framework.util.file.FileUtils;
import com.caozj.framework.util.json.JsonUtil;

/**
 * 流程管理Controller
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processManagement")
public class ProcessManagementController {

  private static final Log logger = LogFactory.getLog(ProcessManagementController.class);

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private ProcessService processService;

  @RequestMapping
  public String list(String errorMsg, ModelMap model) {
    model.put("errorMsg", errorMsg);
    return "activiti/processManagementList";
  }

  @RequestMapping("/listDataOfEasyUI.do")
  public String listDataOfEasyUI(ModelMap model, EasyUIPageForm form, String processName) {
    List<ProcessDefinition> list = null;
    long total = 0;
    ProcessDefinitionQuery processDefinitionQuery =
        repositoryService.createProcessDefinitionQuery().latestVersion();
    if (StringUtils.isNotEmpty(processName)) {
      String processNameLike = "%" + processName + "%";
      processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike(processNameLike);
    }
    list = processDefinitionQuery.listPage((form.getPage() - 1) * form.getRows(), form.getRows());
    total = processDefinitionQuery.count();
    List<ProcessVo> voList = new ArrayList<>(list.size());
    list.forEach((pd) -> {
      ProcessVo vo = ActivitiObjectUtil.buildProcessVo(pd);
      voList.add(vo);
    });
    String message = JsonUtil.toJson(new PageGrid(NumberUtils.toInt(total + ""), voList));
    model.put("message", message);
    return "message";
  }

  /**
   * 上传流程
   * 
   * @param processFile
   * @return
   * @throws IOException
   */
  @RequestMapping
  public String uploadProcess(MultipartFile processFile) throws IOException {
    String errorMsg = StringUtils.EMPTY;
    String fileName = processFile.getOriginalFilename();
    String fileType = FileUtils.getFileExt(fileName);
    // 检查文件类型
    if (!".xml".equals(fileType) && !".zip".equals(fileType) && !".bar".equals(fileType)
        && !".bpmn".equals(fileType)) {
      errorMsg = "流程文件类型错误";
      return "redirect:/processManagement/list.do?errorMsg=" + errorMsg;
    }
    // 部署流程
    DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
    InputStream inputStream = processFile.getInputStream();
    if (!".zip".equals(fileType) && !".bar".equals(fileType)) {
      deploymentBuilder.addInputStream(fileName, inputStream);
    } else {
      deploymentBuilder.addZipInputStream(new ZipInputStream(inputStream));
    }
    deploymentBuilder.deploy();
    return "redirect:/processManagement/list.do";
  }

  /**
   * 获取流程的xml内容
   * 
   * @param id 流程定义ID
   * @return
   * @throws IOException
   */
  @RequestMapping
  public String getProcessXml(String id, ModelMap model) throws IOException {
    ProcessDefinition pd =
        repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
    if (pd == null) {
      throw new RuntimeException("流程不存在");
    }
    InputStream inputStream =
        repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getResourceName());
    String xml = new String(StreamUtils.copyToByteArray(inputStream));
    model.put("message", new JsonResult(xml).toJson());
    logger.info("流程xml内容为" + xml);
    return "message";
  }

  /**
   * 获取流程的图片
   * 
   * @param id
   * @param response
   * @throws IOException
   */
  @RequestMapping
  public void getProcessImage(String id, HttpServletResponse response) throws IOException {
    ProcessDefinition pd =
        repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
    if (pd == null) {
      throw new RuntimeException("流程不存在");
    }
    if (StringUtils.isEmpty(pd.getDiagramResourceName())) {
      throw new RuntimeException("流程图片不存在");
    }
    InputStream inputStream =
        repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
    IOUtils.copy(inputStream, response.getOutputStream());
  }

  /**
   * 批量删除流程
   * 
   * @param ids 流程id列表
   * @param model
   * @return
   */
  @RequestMapping
  public String batchDeleteProcess(String[] ids, ModelMap model) {
    processService.batchDeleteProcess(Arrays.asList(ids));
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 启动流程定义
   * 
   * @param id 流程id
   * @param model
   * @return
   */
  @RequestMapping
  public String startProcess(String id, ModelMap model) {
    repositoryService.activateProcessDefinitionById(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }

  /**
   * 暂停流程定义
   * 
   * @param model
   * @return
   */
  @RequestMapping
  public String stopProcess(String id, ModelMap model) {
    repositoryService.suspendProcessDefinitionById(id);
    model.put("message", new JsonResult().toJson());
    return "message";
  }
}
