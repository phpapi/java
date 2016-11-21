package com.caozj.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caozj.dao.DemoGenerDao;
import com.caozj.framework.util.jdbc.Jdbc;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.framework.util.jdbc.StatementParameter;
import com.caozj.model.DemoGener;

/**
 * 
 * 
 * @author caozj
 *  
 */
@Repository
public class DemoGenerDaoImpl implements DemoGenerDao {

	@Autowired
	private Jdbc jdbc;

	private static final String table = "demoGener";

	@Override
	public void add(DemoGener demoGener) {
		String sql = "insert into " + table + "( name, age, num, d, f) values(?,?,?,?,?)";
		jdbc.updateForBoolean(sql, demoGener.getName(),demoGener.getAge(),demoGener.getNum(),demoGener.getD(),demoGener.getF());
	}

	@Override
	public void update(DemoGener demoGener) {
		String sql = "update " + table + " set  name = ? , age = ? , num = ? , d = ? , f = ?  where id = ?  ";
		jdbc.updateForBoolean(sql,demoGener.getName(),demoGener.getAge(),demoGener.getNum(),demoGener.getD(),demoGener.getF(), demoGener.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "delete from " + table + " where id = ? ";
		jdbc.updateForBoolean(sql, id);
	}

	@Override
	public List<DemoGener> page(Pager page) {
		String sql = "select * from " + table;
		return jdbc.queryForPage(sql, DemoGener.class, page);
	}

	@Override
	public List<DemoGener> listAll(){
		String sql = "select * from " + table;
		return jdbc.queryForList(sql, DemoGener.class);
	}

	@Override
	public int count(){
		String sql = "select count(*) from " + table;
		return jdbc.queryForInt(sql); 
	}

	@Override
	public DemoGener get(int id) {
		String sql = "select * from " + table + " where id = ? ";
		return jdbc.query(sql, DemoGener.class, id);
	}
	
	@Override
	public DemoGener getBy(String field, Object value) {
		String sql = "select * from " + table + " where " + field + " = ? ";
		return jdbc.query(sql, DemoGener.class, value);
	}

	@Override
	public DemoGener getByAnd(String field1, Object value1, String field2, Object value2) {
		String sql = "select * from " + table + " where " + field1 + " = ? and " + field2 + " = ? ";
		return jdbc.query(sql, DemoGener.class, value1, value2);
	}

	@Override
	public DemoGener getByOr(String field1, Object value1, String field2, Object value2) {
		String sql = "select * from " + table + " where " + field1 + " = ? or " + field2 + " = ? ";
		return jdbc.query(sql, DemoGener.class, value1, value2);
	}

	@Override
	public List<DemoGener> listBy(String field, Object value) {
		String sql = "select * from " + table + " where " + field + " = ? ";
		return jdbc.queryForList(sql, DemoGener.class, value);
	}

	@Override
	public List<DemoGener> listByAnd(String field1, Object value1, String field2, Object value2) {
		String sql = "select * from " + table + " where " + field1 + " = ? and " + field2 + " = ? ";
		return jdbc.queryForList(sql, DemoGener.class, value1, value2);
	}

	@Override
	public List<DemoGener> listByOr(String field1, Object value1, String field2, Object value2) {
		String sql = "select * from " + table + " where " + field1 + " = ? or " + field2 + " = ? ";
		return jdbc.queryForList(sql, DemoGener.class, value1, value2);
	}

	@Override
	public List<DemoGener> pageBy(String field, Object value, Pager page) {
		String sql = "select * from " + table + " where " + field + " = ? ";
		StatementParameter param = new StatementParameter();
		param.set(value);
		return jdbc.queryForPage(sql, DemoGener.class, page, param);
	}

	@Override
	public List<DemoGener> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page) {
		String sql = "select * from " + table + " where " + field1 + " = ? and " + field2 + " = ? ";
		StatementParameter param = new StatementParameter();
		param.set(value1);
		param.set(value2);
		return jdbc.queryForPage(sql, DemoGener.class, page, param);
	}

	@Override
	public List<DemoGener> pageByOr(String field1, Object value1, String field2, Object value2, Pager page) {
		String sql = "select * from " + table + " where " + field1 + " = ? or " + field2 + " = ? ";
		StatementParameter param = new StatementParameter();
		param.set(value1);
		param.set(value2);
		return jdbc.queryForPage(sql, DemoGener.class, page, param);
	}
	

}
