package com.caozj.activiti.dao;

import com.caozj.activiti.model.ProcessSet;

/**
 * 流程设置Dao
 * 
 * @author caozj
 * 
 */
public interface ProcessSetDao {

  void add(ProcessSet processSet);

  void update(ProcessSet processSet);

  void delete(String id);

  ProcessSet get(String id);

}
