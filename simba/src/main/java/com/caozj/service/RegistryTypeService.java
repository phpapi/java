package  com.caozj.service;

import java.util.List;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.RegistryType;

/**
 *注册类型 Service
 * 
 * @author caozj
 * 
 */
public interface RegistryTypeService {

	void add(RegistryType registryType);

	void update(RegistryType registryType);

	void delete(int id);

	List<RegistryType> listAll();

	int count();
	
	int countBy(String field, Object value);
	
	List<RegistryType> page(Pager page);

	RegistryType get(int id);
	
	void batchDelete(List<Integer> idList);

	RegistryType getBy(String field, Object value);

	RegistryType getByAnd(String field1, Object value1, String field2, Object value2);

	RegistryType getByOr(String field1, Object value1, String field2, Object value2);

	List<RegistryType> listBy(String field, Object value);

	List<RegistryType> listByAnd(String field1, Object value1, String field2, Object value2);

	List<RegistryType> listByOr(String field1, Object value1, String field2, Object value2);

	List<RegistryType> pageBy(String field, Object value, Pager page);

	List<RegistryType> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page);

	List<RegistryType> pageByOr(String field1, Object value1, String field2, Object value2, Pager page);
	List<RegistryType> listChildren(int parentID);
}
