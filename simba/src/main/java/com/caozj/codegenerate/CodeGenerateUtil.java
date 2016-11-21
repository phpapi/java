package com.caozj.codegenerate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caozj.common.CustomizedPropertyPlaceholderConfigurer;
import com.caozj.framework.util.common.AnnotationUtil;
import com.caozj.framework.util.common.ReflectUtil;
import com.caozj.framework.util.common.ServerUtil;
import com.caozj.framework.util.common.StringUtil;
import com.caozj.framework.util.freemarker.FreemarkerUtil;
import com.caozj.model.constant.ConstantData;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 代码生成器工具类
 * 
 * @author caozj
 *
 */
public class CodeGenerateUtil {

  private static final Log logger = LogFactory.getLog(CodeGenerateUtil.class);

  private static final String mybatisXmlDir = "mybatis";

  private String packageName =
      CustomizedPropertyPlaceholderConfigurer.getContextProperty("code.generate.package");

  private static class CodeGenerateUtilHolder {
    private static CodeGenerateUtil instance = new CodeGenerateUtil();
  }

  private CodeGenerateUtil() {

  }

  public static CodeGenerateUtil getInstance() {
    return CodeGenerateUtilHolder.instance;
  }

  /**
   * 按照class对象生成代码
   * 
   * @param c class对象
   * @param dir 代码输入目录
   * @throws IOException
   * @throws TemplateException
   */
  public void codeGenerate(Class<?> c, File dir, CODETYPE type, PAGETYPE pageType)
      throws IOException, TemplateException {
    logger.info(
        "生成代码" + c.getName() + "开始,代码类型为" + type.getFolderName() + ",页面类型为" + pageType.getName());
    initPath(dir, type, pageType, c);
    Map<String, Object> param = buildParam(c, pageType);
    generateClassFile(dir, param, type);
    if (pageType != PAGETYPE.NONE) {
      generatePageFile(param);
    }
    logger.info(
        "生成代码" + c.getName() + "结束,代码类型为" + type.getFolderName() + ",页面类型为" + pageType.getName());
  }

  /**
   * 生成页面文件
   * 
   * @param param
   * @throws IOException
   * @throws TemplateException
   */
  private void generatePageFile(Map<String, Object> param) throws IOException, TemplateException {
    String firstLower = param.get("firstLower").toString();
    String jsDir = ServerUtil.getWebRoot() + "/js/app";
    String jspDir = ServerUtil.getWebRoot() + "/WEB-INF/jsp/" + firstLower;
    String jsFile = jsDir + "/" + firstLower + ".js";
    String addFile = jspDir + "/add.jsp";
    String updateFile = jspDir + "/update.jsp";
    String listFile = jspDir + "/list.jsp";
    String jsContent = getJs(param);
    String addContent = getAdd(param);
    String updateContent = getUpdate(param);
    String listContent = getList(param);
    FileUtils.writeStringToFile(new File(jsFile), jsContent, ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + jsFile);
    FileUtils.writeStringToFile(new File(addFile), addContent, ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + addFile);
    FileUtils.writeStringToFile(new File(updateFile), updateContent, ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + updateFile);
    FileUtils.writeStringToFile(new File(listFile), listContent, ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + listFile);
  }

  private String getList(Map<String, Object> param) throws TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, IOException, TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/jsp/list.ftl", param);
  }

  private String getUpdate(Map<String, Object> param) throws TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, IOException, TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/jsp/update.ftl", param);
  }

  private String getAdd(Map<String, Object> param) throws TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, IOException, TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/jsp/add.ftl", param);
  }

  private String getJs(Map<String, Object> param) throws TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, IOException, TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/js/js.ftl", param);
  }

  /**
   * 构造参数
   * 
   * @param c
   * @return
   */
  private Map<String, Object> buildParam(Class<?> c, PAGETYPE pageType) {
    Map<String, Object> param = new HashMap<String, Object>();
    String className = c.getSimpleName();
    String firstLower = StringUtil.getFirstLower(className);
    DescAnnotation descAnnotation = AnnotationUtil.getClassAnnotation(c, DescAnnotation.class);
    if (descAnnotation == null) {
      param.put("classDesc", "");
    } else {
      param.put("classDesc", descAnnotation.desc());
    }
    param.put("className", className);
    param.put("firstLower", firstLower);
    param.put("packageName", packageName);
    param.put("pageType", pageType.getName());
    List<String> fields = ReflectUtil.getAllPropertiesName(c);
    List<Map<String, String>> filedsWithPage = new ArrayList<>();
    String updateProperties = "";
    String insertProperties = "";
    String params = "";
    String propertiesCount = "";
    for (String field : fields) {
      if ("id".equals(field) || "serialVersionUID".equals(field)) {
        continue;
      }
      if (pageType == PAGETYPE.TREETABLE && "state".equals(field)) {
        continue;
      }
      if (!"parentID".equals(field)) {
        Map<String, String> fDesc = new HashMap<>(2);
        fDesc.put("key", field);
        DescAnnotation da = AnnotationUtil.getFiledAnnotion(c, field, DescAnnotation.class);
        if (da == null) {
          fDesc.put("desc", field);
        } else {
          fDesc.put("desc", da.desc());
        }
        filedsWithPage.add(fDesc);
      }
      updateProperties += " " + field + " = ? ,";
      insertProperties += " " + field + ",";
      propertiesCount += "?,";
      params += firstLower + ".get" + StringUtil.getFirstUpper(field) + "(),";
    }
    updateProperties = updateProperties.substring(0, updateProperties.length() - 1);
    insertProperties = insertProperties.substring(0, insertProperties.length() - 1);
    propertiesCount = propertiesCount.substring(0, propertiesCount.length() - 1);
    params = params.substring(0, params.length() - 1);
    param.put("updateProperties", updateProperties);
    param.put("insertProperties", insertProperties);
    param.put("params", params);
    param.put("propertiesCount", propertiesCount);
    param.put("filedsWithPage", filedsWithPage);
    return param;
  }

  /**
   * 生成类文件
   * 
   * @param dir
   * @param className
   * @param param
   * @throws IOException
   * @throws TemplateException
   */
  private void generateClassFile(File dir, Map<String, Object> param, CODETYPE type)
      throws IOException, TemplateException {
    String className = param.get("className").toString();
    String controllerContent = getController(param, type);
    String serviceContent = getService(param, type);
    String serviceImplContent = getServiceImpl(param, type);
    String daoContent = getDao(param, type);
    String controllerFile = dir.getAbsoluteFile() + "/controller/" + className + "Controller.java";
    FileUtils.writeStringToFile(new File(controllerFile), controllerContent,
        ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + controllerFile);
    String serviceFile = dir.getAbsoluteFile() + "/service/" + className + "Service.java";
    FileUtils.writeStringToFile(new File(serviceFile), serviceContent,
        ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + serviceFile);
    String serviceImplFile =
        dir.getAbsoluteFile() + "/service/impl/" + className + "ServiceImpl.java";
    FileUtils.writeStringToFile(new File(serviceImplFile), serviceImplContent,
        ConstantData.DEFAULT_CHARSET);
    logger.info("生成" + serviceImplFile);
    if (type == CODETYPE.JDBC) {
      String daoImplContent = getDaoImpl(param, type);
      String daoFile = dir.getAbsoluteFile() + "/dao/" + className + "Dao.java";
      FileUtils.writeStringToFile(new File(daoFile), daoContent, ConstantData.DEFAULT_CHARSET);
      logger.info("生成" + daoFile);
      String daoImplFile = dir.getAbsoluteFile() + "/dao/impl/" + className + "DaoImpl.java";
      FileUtils.writeStringToFile(new File(daoImplFile), daoImplContent,
          ConstantData.DEFAULT_CHARSET);
      logger.info("生成" + daoImplFile);
    } else if (type == CODETYPE.MYBATIS) {
      String mybatisDaoFile = dir.getAbsoluteFile() + "/mybatisDao/" + className + "Mapper.java";
      FileUtils.writeStringToFile(new File(mybatisDaoFile), daoContent,
          ConstantData.DEFAULT_CHARSET);
      logger.info("生成" + mybatisDaoFile);
      String mybatisDaoXmlContent = getMybatisDaoXml(param, type);
      String mybatisXmlFile = ServerUtil.getResourcesDir().getAbsolutePath() + "/" + mybatisXmlDir
          + "/" + StringUtil.getFirstLower(className) + ".xml";
      FileUtils.writeStringToFile(new File(mybatisXmlFile), mybatisDaoXmlContent,
          ConstantData.DEFAULT_CHARSET);
      logger.info("生成" + mybatisXmlFile);
    }
  }

  private String getMybatisDaoXml(Map<String, Object> param, CODETYPE type)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
      TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/mybatisXml.ftl",
        param);
  }

  private String getController(Map<String, Object> param, CODETYPE type)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
      TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/controller.ftl",
        param);
  }

  private String getService(Map<String, Object> param, CODETYPE type)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
      TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/service.ftl", param);
  }

  private String getServiceImpl(Map<String, Object> param, CODETYPE type)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
      TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/serviceImpl.ftl",
        param);
  }

  private String getDao(Map<String, Object> param, CODETYPE type) throws TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, IOException, TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/dao.ftl", param);
  }

  private String getDaoImpl(Map<String, Object> param, CODETYPE type)
      throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
      TemplateException {
    return FreemarkerUtil.parseFile("codegenerate/" + type.getFolderName() + "/daoImpl4Mysql.ftl",
        param);
  }

  /**
   * 创建好所有需要的路径
   */
  private void initPath(File dir, CODETYPE type, PAGETYPE pageType, Class<?> c) {
    String root = dir.getAbsolutePath();
    String service = root + "/service";
    String serviceImpl = service + "/impl";
    String controller = root + "/controller";
    new File(serviceImpl).mkdirs();
    new File(controller).mkdirs();
    if (type == CODETYPE.JDBC) {
      String dao = root + "/dao";
      String daoImpl = dao + "/impl";
      new File(daoImpl).mkdirs();
    } else if (type == CODETYPE.MYBATIS) {
      String dao = root + "/mybatisDao";
      new File(dao).mkdirs();
      new File(ServerUtil.getResourcesDir().getAbsolutePath() + "/" + mybatisXmlDir).mkdirs();
    }
    if (pageType != PAGETYPE.NONE) {
      String jsDir = ServerUtil.getWebRoot() + "/js/app";
      new File(jsDir).mkdirs();
      String jspDir =
          ServerUtil.getWebRoot() + "/WEB-INF/jsp/" + StringUtil.getFirstLower(c.getSimpleName());
      new File(jspDir).mkdirs();
    }
  }

  /**
   * 按照class对象数组生成代码
   * 
   * @param cs class对象数组
   * @param dir 代码输入目录
   * @throws IOException
   * @throws TemplateException
   */
  public void codeGenerate(Class<?>[] cs, File dir, CODETYPE type, PAGETYPE pageType)
      throws IOException, TemplateException {
    for (Class<?> c : cs) {
      codeGenerate(c, dir, type, pageType);
    }
  }

  /**
   * 按照class对象生成代码
   * 
   * @param c class对象
   * @throws IOException
   * @throws TemplateException
   */
  public void codeGenerate(Class<?> c, CODETYPE type, PAGETYPE pageType)
      throws IOException, TemplateException {
    codeGenerate(c, new File(ServerUtil.getSrcDir().getAbsolutePath() + "/" + getPackagePath()),
        type, pageType);
  }

  /**
   * 按照class对象数组生成代码
   * 
   * @param cs class对象数组
   * @throws IOException
   * @throws TemplateException
   */
  public void codeGenerate(Class<?>[] cs, CODETYPE type, PAGETYPE pageType)
      throws IOException, TemplateException {
    codeGenerate(cs, new File(ServerUtil.getSrcDir().getAbsolutePath() + "/" + getPackagePath()),
        type, pageType);
  }

  private String getPackagePath() {
    return packageName.replaceAll("\\.", "/");
  }
}
