package com.caozj.activiti.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

/**
 * 流程Service
 * 
 * @author caozj
 *
 */
public interface ProcessService {

  /**
   * 批量删除流程
   * 
   * @param processIDs 流程ID列表
   */
  void batchDeleteProcess(List<String> processIDs);

  /**
   * 删除流程
   * 
   * @param processID 流程ID
   */
  void deleteProcess(String processID);

  /**
   * 保存启动流程
   * 
   * @param processID 流程ID
   * @param params 参数对象
   * @param account 启动流程的账号
   * 
   * @return 第一个活动的任务
   */
  Task saveStartProcess(String processID, Map<String, String> params, String account);

  /**
   * 提交启动流程
   * 
   * @param processID 流程ID
   * @param params 参数对象
   * @param account 启动流程的账号
   * 
   */
  void submitStartProcess(String processID, Map<String, String> params, String account);

  /**
   * 保存任务
   * 
   * @param task 任务
   * @param params 参数对象
   * @param account 执行任务的账号
   */
  void saveTask(Task task, Map<String, String> params, String account);

  /**
   * 提交任务
   * 
   * @param task 任务
   * @param params 参数对象
   * @param account 执行任务的账号
   */
  void submitTask(Task task, Map<String, String> params, String account);
}
