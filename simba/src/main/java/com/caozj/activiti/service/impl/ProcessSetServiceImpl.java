package com.caozj.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caozj.activiti.dao.ProcessSetDao;
import com.caozj.activiti.model.ProcessSet;
import com.caozj.activiti.service.ProcessSetService;

/**
 * Service实现类
 * 
 * @author caozj
 * 
 */
@Service
@Transactional
public class ProcessSetServiceImpl implements ProcessSetService {

  @Autowired
  private ProcessSetDao processSetDao;

  @Override
  public void add(ProcessSet processSet) {
    processSetDao.add(processSet);
  }

  @Override
  public void delete(String id) {
    processSetDao.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public ProcessSet get(String id) {
    return processSetDao.get(id);
  }

  @Override
  public void update(ProcessSet processSet) {
    processSetDao.update(processSet);
  }

}
