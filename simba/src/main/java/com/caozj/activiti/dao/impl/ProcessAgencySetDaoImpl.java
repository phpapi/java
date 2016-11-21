package com.caozj.activiti.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caozj.activiti.dao.ProcessAgencySetDao;
import com.caozj.activiti.model.ProcessAgencySet;
import com.caozj.framework.util.jdbc.Jdbc;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.framework.util.jdbc.StatementParameter;

/**
 * Dao实现类
 * 
 * @author caozj
 * 
 */
@Repository
public class ProcessAgencySetDaoImpl implements ProcessAgencySetDao {

  @Autowired
  private Jdbc jdbc;

  private static final String table = "processAgencySet";

  @Override
  public void add(ProcessAgencySet processAgencySet) {
    String sql =
        "insert into " + table + "( startTime, endTime, account, agencyAccount) values(?,?,?,?)";
    jdbc.updateForBoolean(sql, processAgencySet.getStartTime(), processAgencySet.getEndTime(),
        processAgencySet.getAccount(), processAgencySet.getAgencyAccount());
  }

  @Override
  public void update(ProcessAgencySet processAgencySet) {
    String sql = "update " + table
        + " set  startTime = ? , endTime = ? , account = ? , agencyAccount = ?  where id = ?  ";
    jdbc.updateForBoolean(sql, processAgencySet.getStartTime(), processAgencySet.getEndTime(),
        processAgencySet.getAccount(), processAgencySet.getAgencyAccount(),
        processAgencySet.getId());
  }

  @Override
  public void delete(int id) {
    String sql = "delete from " + table + " where id = ? ";
    jdbc.updateForBoolean(sql, id);
  }

  @Override
  public List<ProcessAgencySet> page(Pager page) {
    String sql = "select * from " + table;
    return jdbc.queryForPage(sql, ProcessAgencySet.class, page);
  }

  @Override
  public List<ProcessAgencySet> listAll() {
    String sql = "select * from " + table;
    return jdbc.queryForList(sql, ProcessAgencySet.class);
  }

  @Override
  public int count() {
    String sql = "select count(*) from " + table;
    return jdbc.queryForInt(sql);
  }

  @Override
  public ProcessAgencySet get(int id) {
    String sql = "select * from " + table + " where id = ? ";
    return jdbc.query(sql, ProcessAgencySet.class, id);
  }

  @Override
  public ProcessAgencySet getBy(String field, Object value) {
    String sql = "select * from " + table + " where " + field + " = ? ";
    return jdbc.query(sql, ProcessAgencySet.class, value);
  }

  @Override
  public ProcessAgencySet getByAnd(String field1, Object value1, String field2, Object value2) {
    String sql = "select * from " + table + " where " + field1 + " = ? and " + field2 + " = ? ";
    return jdbc.query(sql, ProcessAgencySet.class, value1, value2);
  }

  @Override
  public ProcessAgencySet getByOr(String field1, Object value1, String field2, Object value2) {
    String sql = "select * from " + table + " where " + field1 + " = ? or " + field2 + " = ? ";
    return jdbc.query(sql, ProcessAgencySet.class, value1, value2);
  }

  @Override
  public List<ProcessAgencySet> listBy(String field, Object value) {
    String sql = "select * from " + table + " where " + field + " = ? ";
    return jdbc.queryForList(sql, ProcessAgencySet.class, value);
  }

  @Override
  public List<ProcessAgencySet> listByAnd(String field1, Object value1, String field2,
      Object value2) {
    String sql = "select * from " + table + " where " + field1 + " = ? and " + field2 + " = ? ";
    return jdbc.queryForList(sql, ProcessAgencySet.class, value1, value2);
  }

  @Override
  public List<ProcessAgencySet> listByOr(String field1, Object value1, String field2,
      Object value2) {
    String sql = "select * from " + table + " where " + field1 + " = ? or " + field2 + " = ? ";
    return jdbc.queryForList(sql, ProcessAgencySet.class, value1, value2);
  }

  @Override
  public List<ProcessAgencySet> pageBy(String field, Object value, Pager page) {
    String sql = "select * from " + table + " where " + field + " = ? ";
    StatementParameter param = new StatementParameter();
    param.set(value);
    return jdbc.queryForPage(sql, ProcessAgencySet.class, page, param);
  }

  @Override
  public List<ProcessAgencySet> pageByAnd(String field1, Object value1, String field2,
      Object value2, Pager page) {
    String sql = "select * from " + table + " where " + field1 + " = ? and " + field2 + " = ? ";
    StatementParameter param = new StatementParameter();
    param.set(value1);
    param.set(value2);
    return jdbc.queryForPage(sql, ProcessAgencySet.class, page, param);
  }

  @Override
  public List<ProcessAgencySet> pageByOr(String field1, Object value1, String field2, Object value2,
      Pager page) {
    String sql = "select * from " + table + " where " + field1 + " = ? or " + field2 + " = ? ";
    StatementParameter param = new StatementParameter();
    param.set(value1);
    param.set(value2);
    return jdbc.queryForPage(sql, ProcessAgencySet.class, page, param);
  }

  @Override
  public int countBy(String field, Object value) {
    String sql = "select count(*) from " + table + " where " + field + " = ? ";
    return jdbc.queryForInt(sql, value);
  }

}
