package com.caozj.service;

import java.util.List;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.RegistryTable;

/**
 * 注册表 Service
 * 
 * @author caozj
 * 
 */
public interface RegistryTableService {

	void add(RegistryTable registryTable);

	void update(RegistryTable registryTable);

	void delete(int id);

	List<RegistryTable> listAll();

	int count();

	int countBy(String field, Object value);

	List<RegistryTable> page(Pager page);

	RegistryTable get(int id);

	void batchDelete(List<Integer> idList);

	RegistryTable getBy(String field, Object value);

	RegistryTable getByAnd(String field1, Object value1, String field2, Object value2);

	RegistryTable getByOr(String field1, Object value1, String field2, Object value2);

	List<RegistryTable> listBy(String field, Object value);

	List<RegistryTable> listByAnd(String field1, Object value1, String field2, Object value2);

	List<RegistryTable> listByOr(String field1, Object value1, String field2, Object value2);

	List<RegistryTable> pageBy(String field, Object value, Pager page);

	List<RegistryTable> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page);

	List<RegistryTable> pageByOr(String field1, Object value1, String field2, Object value2, Pager page);

	/**
	 * 初始化注册表数据到内存中
	 */
	void init();
}
