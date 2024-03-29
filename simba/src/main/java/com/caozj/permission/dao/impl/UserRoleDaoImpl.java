package com.caozj.permission.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caozj.framework.util.jdbc.Jdbc;
import com.caozj.permission.dao.UserRoleDao;
import com.caozj.permission.model.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private Jdbc jdbc;

	private static final String table = "userRole";

	@Override
	public void add(UserRole userRole) {
		String sql = "insert into " + table + "(userAccount,roleName) values(?,?)";
		jdbc.updateForBoolean(sql, userRole.getUserAccount(), userRole.getRoleName());
	}

	@Override
	public void deleteByUserAccount(String userAccount) {
		String sql = "delete from " + table + " where userAccount = ? ";
		jdbc.updateForBoolean(sql, userAccount);
	}

	@Override
	public void deleteByRole(String roleName) {
		String sql = "delete from " + table + " where roleName = ? ";
		jdbc.updateForBoolean(sql, roleName);
	}

	@Override
	public List<UserRole> listByUserAccount(String userAccount) {
		String sql = "select * from " + table + " where userAccount = ? ";
		return jdbc.queryForList(sql, UserRole.class, userAccount);
	}

}
