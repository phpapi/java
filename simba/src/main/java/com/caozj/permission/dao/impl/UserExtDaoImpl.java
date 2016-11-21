package com.caozj.permission.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caozj.framework.util.jdbc.Jdbc;
import com.caozj.framework.util.jdbc.StatementParameter;
import com.caozj.permission.dao.UserExtDao;
import com.caozj.permission.model.UserExt;
import com.caozj.permission.model.UserExtDesc;

/**
 * 
 * 
 * @author caozj
 * 
 */
@Repository
public class UserExtDaoImpl implements UserExtDao {

	@Autowired
	private Jdbc jdbc;

	private static final String table = "userExt";

	@Override
	public List<String> showAllColumns() {
		String sql = "select column_name from information_schema.COLUMNS where table_name = ?";
		return jdbc.queryForStrings(sql, table);
	}

	@Override
	public void addColumn(String column) {
		String sql = "ALTER TABLE " + table + " ADD " + column + " varchar(256)";
		jdbc.updateForBoolean(sql);
	}

	@Override
	public UserExt get(String userAccount) {
		String sql = "select * from " + table + " where userAccount = ? ";
		Map<String, Object> ext = jdbc.queryForMap(sql, userAccount);
		UserExt userExt = new UserExt();
		userExt.setUserAccount(userAccount);
		Map<String, String> extMap = new HashMap<>();
		userExt.setExtMap(extMap);
		if (ext != null && ext.size() > 0) {
			ext.forEach((key, value) -> {
				extMap.put(key, value + "");
			});
		}
		return userExt;
	}

	@Override
	public void delete(String account) {
		String sql = "delete from " + table + " where userAccount = ?";
		jdbc.updateForBoolean(sql, account);
	}

	@Override
	public void add(UserExt userExt) {
		String sql = "insert into " + table + "(userAccount";
		Map<String, String> extMap = userExt.getExtMap();
		String columns = "";
		String params = "values(?";
		Map<String, String> descMap = UserExtDesc.getAllDesc();
		StatementParameter param = new StatementParameter();
		param.setString(userExt.getUserAccount());
		for (String key : descMap.keySet()) {
			columns += "," + key;
			param.set(extMap.get(key));
			params += ",?";
		}
		sql = sql + columns + ")" + params + ")";
		jdbc.updateForBoolean(sql, param);
	}

	@Override
	public void update(UserExt userExt) {
		Map<String, String> descMap = UserExtDesc.getAllDesc();
		if (descMap.size() == 0) {
			return;
		}
		String sql = "update " + table + " set ";
		Map<String, String> extMap = userExt.getExtMap();
		StatementParameter param = new StatementParameter();
		for (String key : descMap.keySet()) {
			sql += key + " = ?,";
			param.set(extMap.get(key));
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " where userAccount = ? ";
		param.setString(userExt.getUserAccount());
		jdbc.updateForBoolean(sql, param);
	}

}
