package com.caozj.activiti.util;

import java.util.HashMap;
import java.util.Map;

import com.caozj.activiti.interfaces.SendUser;

/**
 * 保存发送下一步执行人的数据对象(单例模式)
 * 
 * @author caozj
 *
 */
public class SendUserData {

  private SendUserData() {

  }

  private static final class SendUserDataHolder {
    private static SendUserData instance = new SendUserData();
  }

  public static SendUserData getInstance() {
    return SendUserDataHolder.instance;
  }

  private Map<String, SendUser> processUserMap = new HashMap<>();

  public void add(String processKey, SendUser sendUser) {
    processUserMap.put(processKey, sendUser);
  }

  public SendUser get(String processKey) {
    return processUserMap.get(processKey);
  }
}
