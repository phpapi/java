package  com.caozj.service;

import java.util.List;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.DemoGener;

/**
 *
 * 
 * @author caozj
 * 
 */
public interface DemoGenerService {

	void add(DemoGener demoGener);

	void update(DemoGener demoGener);

	void delete(int id);

	List<DemoGener> listAll();

	int count();
	
	List<DemoGener> page(Pager page);

	DemoGener get(int id);
	
	void batchDelete(List<Integer> idList);

	DemoGener getBy(String field, Object value);

	DemoGener getByAnd(String field1, Object value1, String field2, Object value2);

	DemoGener getByOr(String field1, Object value1, String field2, Object value2);

	List<DemoGener> listBy(String field, Object value);

	List<DemoGener> listByAnd(String field1, Object value1, String field2, Object value2);

	List<DemoGener> listByOr(String field1, Object value1, String field2, Object value2);

	List<DemoGener> pageBy(String field, Object value, Pager page);

	List<DemoGener> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page);

	List<DemoGener> pageByOr(String field1, Object value1, String field2, Object value2, Pager page);
}
