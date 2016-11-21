package com.caozj.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caozj.activiti.dao.ProcessAgencySetDao;
import com.caozj.activiti.model.ProcessAgencySet;
import com.caozj.activiti.service.ProcessAgencySetService;
import com.caozj.framework.util.jdbc.Pager;

/**
 * Service实现类
 * 
 * @author caozj
 * 
 */
@Service
@Transactional
public class ProcessAgencySetServiceImpl implements ProcessAgencySetService {

  @Autowired
  private ProcessAgencySetDao processAgencySetDao;

  @Override
  public void add(ProcessAgencySet processAgencySet) {
    processAgencySetDao.add(processAgencySet);
  }

  @Override
  public void delete(int id) {
    processAgencySetDao.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public ProcessAgencySet get(int id) {
    return processAgencySetDao.get(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> page(Pager page) {
    return processAgencySetDao.page(page);
  }

  @Override
  @Transactional(readOnly = true)
  public int count() {
    return processAgencySetDao.count();
  }

  @Override
  @Transactional(readOnly = true)
  public int countBy(String field, Object value) {
    return processAgencySetDao.countBy(field, value);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> listAll() {
    return processAgencySetDao.listAll();
  }

  @Override
  public void update(ProcessAgencySet processAgencySet) {
    processAgencySetDao.update(processAgencySet);
  }

  @Override
  public void batchDelete(List<Integer> idList) {
    for (Integer id : idList) {
      this.delete(id);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ProcessAgencySet getBy(String field, Object value) {
    return processAgencySetDao.getBy(field, value);
  }

  @Override
  @Transactional(readOnly = true)
  public ProcessAgencySet getByAnd(String field1, Object value1, String field2, Object value2) {
    return processAgencySetDao.getByAnd(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public ProcessAgencySet getByOr(String field1, Object value1, String field2, Object value2) {
    return processAgencySetDao.getByOr(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> listBy(String field, Object value) {
    return processAgencySetDao.listBy(field, value);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> listByAnd(String field1, Object value1, String field2,
      Object value2) {
    return processAgencySetDao.listByAnd(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> listByOr(String field1, Object value1, String field2,
      Object value2) {
    return processAgencySetDao.listByOr(field1, value1, field2, value2);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> pageBy(String field, Object value, Pager page) {
    return processAgencySetDao.pageBy(field, value, page);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> pageByAnd(String field1, Object value1, String field2,
      Object value2, Pager page) {
    return processAgencySetDao.pageByAnd(field1, value1, field2, value2, page);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProcessAgencySet> pageByOr(String field1, Object value1, String field2, Object value2,
      Pager page) {
    return processAgencySetDao.pageByOr(field1, value1, field2, value2, page);
  }

}
