package com.caozj.framework.util.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author caozj
 *
 */
public class StringUtil {

  /**
   * 首字母小写
   * 
   * @param content
   * @return
   */
  public static String getFirstLower(String content) {
    return content.substring(0, 1).toLowerCase() + content.substring(1);
  }

  /**
   * 首字母大写
   * 
   * @param content
   * @return
   */
  public static String getFirstUpper(String content) {
    return content.substring(0, 1).toUpperCase() + content.substring(1);
  }

  /**
   * 连接字符串列表
   * 
   * @param list 字符串列表
   * @param split 连接字符
   * @return
   */
  public static String join(List<String> list, String split) {
    if (list == null || list.size() == 0) {
      return StringUtils.EMPTY;
    }
    String result = "";
    for (String s : list) {
      result += s + split;
    }
    if (list.size() > 0) {
      result = result.substring(0, result.length() - split.length() + 1);
    }
    return result;
  }

  /**
   * 连接字符串列表
   * 
   * @param list 字符串列表
   * @param split 连接字符
   * @return
   */
  public static String join(String[] list, String split) {
    if (list == null || list.length == 0) {
      return StringUtils.EMPTY;
    }
    String result = "";
    for (String s : list) {
      result += s + split;
    }
    if (list.length > 0) {
      result = result.substring(0, result.length() - split.length() + 1);
    }
    return result;
  }

}
