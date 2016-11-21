package com.caozj.activiti.service;

import java.util.List;

import com.caozj.activiti.model.ProcessAgencySet;
import com.caozj.framework.util.jdbc.Pager;

/**
 * Service
 * 
 * @author caozj
 * 
 */
public interface ProcessAgencySetService {

  void add(ProcessAgencySet processAgencySet);

  void update(ProcessAgencySet processAgencySet);

  void delete(int id);

  List<ProcessAgencySet> listAll();

  int count();

  int countBy(String field, Object value);

  List<ProcessAgencySet> page(Pager page);

  ProcessAgencySet get(int id);

  void batchDelete(List<Integer> idList);

  ProcessAgencySet getBy(String field, Object value);

  ProcessAgencySet getByAnd(String field1, Object value1, String field2, Object value2);

  ProcessAgencySet getByOr(String field1, Object value1, String field2, Object value2);

  List<ProcessAgencySet> listBy(String field, Object value);

  List<ProcessAgencySet> listByAnd(String field1, Object value1, String field2, Object value2);

  List<ProcessAgencySet> listByOr(String field1, Object value1, String field2, Object value2);

  List<ProcessAgencySet> pageBy(String field, Object value, Pager page);

  List<ProcessAgencySet> pageByAnd(String field1, Object value1, String field2, Object value2,
      Pager page);

  List<ProcessAgencySet> pageByOr(String field1, Object value1, String field2, Object value2,
      Pager page);
}
