package com.caozj.expressage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caozj.common.CustomizedPropertyPlaceholderConfigurer;
import com.caozj.framework.util.file.PropertiesUtil;
import com.caozj.framework.util.http.HttpClientUtil;
import com.caozj.framework.util.json.JsonUtil;

/**
 * 快递查询工具类
 * 
 * @author caozj
 *
 */
public class ExpressageUtil {

  private static final Log logger = LogFactory.getLog(ExpressageUtil.class);

  private List<ExpressageCompany> allCompany;

  private String id;

  private String url;

  private ExpressageUtil() {
    init();
  }

  private void init() {
    Map<String, String> properties = PropertiesUtil.read("/expressage/expressage.properties");
    allCompany = new ArrayList<ExpressageCompany>(properties.size());
    for (Map.Entry<String, String> entry : properties.entrySet()) {
      ExpressageCompany company = new ExpressageCompany();
      company.setCode(entry.getKey());
      company.setName(entry.getValue());
      allCompany.add(company);
    }
    id = CustomizedPropertyPlaceholderConfigurer.getContextProperty("expressage.id");
    url = CustomizedPropertyPlaceholderConfigurer.getContextProperty("expressage.url");
  }

  private static class ExpressageUtilHolder {
    private static final ExpressageUtil instance = new ExpressageUtil();
  }

  public static ExpressageUtil getInstance() {
    return ExpressageUtilHolder.instance;
  }

  /**
   * 获取所有的快递公司
   * 
   * @return
   */
  public List<ExpressageCompany> listAllCompany() {
    return allCompany;
  }

  /**
   * 查询快递
   * 
   * @param company 快递公司编号
   * @param code 单号
   * @return
   */
  public ExpressageCallback search(String company, String code) {
    ExpressageParam param = new ExpressageParam();
    param.setId(id);
    param.setCom(company);
    param.setNu(code);
    String response = HttpClientUtil.get(url, param.toMap());
    logger.info("查询快递单号:" + code + ",返回:" + response);
    return JsonUtil.toObject(response, ExpressageCallback.class);
  }

  public static void main(String[] args) {
    System.out.println(ExpressageUtil.getInstance().search("tiantian", "560389524987").toString());
  }
}
