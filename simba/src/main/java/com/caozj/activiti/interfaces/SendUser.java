package com.caozj.activiti.interfaces;

import org.activiti.engine.task.Task;

/**
 * 获取发送下一步的流程执行人
 * 
 * @author caozj
 *
 */
public interface SendUser {

  /**
   * 获取下一步要执行的人的账号
   * 
   * @param currentUserAccount 执行当前任务的用户账号
   * @param task 下一步的任务对象
   * @return
   */
  String getNextUser(String currentUserAccount, Task task);

  /**
   * 返回流程Key
   * 
   * @return
   */
  String getProcessKey();
}
