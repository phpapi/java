package com.caozj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.caozj.dao.DemoGenerDao;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.DemoGener;
import com.caozj.service.DemoGenerService;

/**
 * 
 * 
 * @author caozj
 * 
 */
@Service
@Transactional
public class DemoGenerServiceImpl implements DemoGenerService {

	@Autowired
	private DemoGenerDao demoGenerDao;

	@Override
	public void add(DemoGener demoGener) {
		demoGenerDao.add(demoGener);
	}

	@Override
	public void delete(int id) {
		demoGenerDao.delete(id);
	}

	@Override
	public DemoGener get(int id) {
		return demoGenerDao.get(id);
	}

	@Override
	public List<DemoGener> page(Pager page) {
		return demoGenerDao.page(page);
	}

	@Override
	public int count() {
		return demoGenerDao.count();
	}

	@Override
	public List<DemoGener> listAll() {
		return demoGenerDao.listAll();
	}

	@Override
	public void update(DemoGener demoGener) {
		demoGenerDao.update(demoGener);
	}
	
	@Override
	public void batchDelete(List<Integer> idList) {
		for (Integer id : idList) {
			this.delete(id);
		}
	}
	
	@Override
	public DemoGener getBy(String field, Object value) {
		return demoGenerDao.getBy(field, value);
	}

	@Override
	public DemoGener getByAnd(String field1, Object value1, String field2, Object value2) {
		return demoGenerDao.getByAnd(field1, value1, field2, value2);
	}

	@Override
	public DemoGener getByOr(String field1, Object value1, String field2, Object value2) {
		return demoGenerDao.getByOr(field1, value1, field2, value2);
	}

	@Override
	public List<DemoGener> listBy(String field, Object value) {
		return demoGenerDao.listBy(field, value);
	}

	@Override
	public List<DemoGener> listByAnd(String field1, Object value1, String field2, Object value2) {
		return demoGenerDao.listByAnd(field1, value1, field2, value2);
	}

	@Override
	public List<DemoGener> listByOr(String field1, Object value1, String field2, Object value2) {
		return demoGenerDao.listByOr(field1, value1, field2, value2);
	}

	@Override
	public List<DemoGener> pageBy(String field, Object value, Pager page) {
		return demoGenerDao.pageBy(field, value, page);
	}

	@Override
	public List<DemoGener> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page) {
		return demoGenerDao.pageByAnd(field1, value1, field2, value2, page);
	}

	@Override
	public List<DemoGener> pageByOr(String field1, Object value1, String field2, Object value2, Pager page) {
		return demoGenerDao.pageByOr(field1, value1, field2, value2, page);
	}
}
