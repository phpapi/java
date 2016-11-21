package com.caozj.activiti.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caozj.activiti.dao.ProcessSetDao;
import com.caozj.activiti.model.ProcessSet;
import com.caozj.framework.util.jdbc.Jdbc;

/**
 * 流程设置Dao实现类
 * 
 * @author caozj
 * 
 */
@Repository
public class ProcessSetDaoImpl implements ProcessSetDao {

  @Autowired
  private Jdbc jdbc;

  private static final String table = "processSet";

  @Override
  public void add(ProcessSet processSet) {
    String sql = "insert into " + table + "( sendType,allStart) values(?,?)";
    jdbc.updateForBoolean(sql, processSet.getSendType(), processSet.getAllStart());
  }

  @Override
  public void update(ProcessSet processSet) {
    String sql = "update " + table + " set  sendType = ? ,allStart=?  where id = ?  ";
    jdbc.updateForBoolean(sql, processSet.getSendType(), processSet.getAllStart(),
        processSet.getId());
  }

  @Override
  public void delete(String id) {
    String sql = "delete from " + table + " where id = ? ";
    jdbc.updateForBoolean(sql, id);
  }

  @Override
  public ProcessSet get(String id) {
    String sql = "select * from " + table + " where id = ? ";
    return jdbc.query(sql, ProcessSet.class, id);
  }

}
