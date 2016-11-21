package com.caozj.test;

import java.io.IOException;
import java.util.Map;

import com.caozj.framework.util.file.PropertiesUtil;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class T {

  public static void main(String[] args) throws TemplateNotFoundException,
      MalformedTemplateNameException, ParseException, IOException, TemplateException {
    Map<String, String> p = PropertiesUtil.readFileByOrder(
        "D:\\local\\develop2.0\\SmartAs\\target\\depoly\\xxx-web\\src\\main\\resources\\config\\app.properties");
    p.forEach((key,value)->{
      System.out.println(key+":"+value);
    });
    p.put("datasource.driverClassName", "com.mysql.jdbc.Driver-abcde");
    PropertiesUtil.saveFileByOrder("D:\\local\\develop2.0\\SmartAs\\target\\depoly\\xxx-web\\src\\main\\resources\\config\\app.properties", p);
  }

}
