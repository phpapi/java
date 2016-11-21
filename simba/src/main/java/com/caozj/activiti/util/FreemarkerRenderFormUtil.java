package com.caozj.activiti.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.springframework.util.StreamUtils;

import com.caozj.framework.util.freemarker.FreemarkerUtil;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * freemarker渲染表单工具类
 * 
 * @author caozj
 *
 */
public class FreemarkerRenderFormUtil {

  /**
   * 渲染表单
   * 
   * @param repositoryService
   * @param properties
   * @param formKey
   * @param deploymentId
   * @return
   * @throws IOException
   * @throws TemplateNotFoundException
   * @throws MalformedTemplateNameException
   * @throws ParseException
   * @throws TemplateException
   */
  public static Object renderForm(RepositoryService repositoryService,
      List<FormProperty> properties, String formKey, String deploymentId) throws IOException,
      TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException {
    Map<String, Object> params = new HashMap<>();
    properties.forEach((p) -> {
      params.put(p.getName(), p.getValue());
    });
    return renderForm(repositoryService, params, formKey, deploymentId);
  }

  /**
   * 渲染表单
   * 
   * @param repositoryService
   * @param params
   * @param formKey
   * @param deploymentId
   * @return
   * @throws IOException
   * @throws TemplateNotFoundException
   * @throws MalformedTemplateNameException
   * @throws ParseException
   * @throws TemplateException
   */
  public static Object renderForm(RepositoryService repositoryService, Map<String, Object> params,
      String formKey, String deploymentId) throws IOException, TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, TemplateException {
    InputStream formContent = repositoryService.getResourceAsStream(deploymentId, formKey);
    String form = new String(StreamUtils.copyToByteArray(formContent));
    return FreemarkerUtil.parseContent(form, params);
  }
}
