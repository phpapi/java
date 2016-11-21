package com.caozj.permission.service;

import java.util.List;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.permission.model.Permission;
import com.caozj.permission.model.Role;

public interface RoleService {

	void add(Role role);

	void update(Role role);

	void delete(String name);

	List<Role> page(Pager page);

	Role get(String name);

	List<Role> listAll();

	void assignPermission(String roleName, List<Integer> permissionIDList);

	void batchDelete(List<String> roleNameList);

	List<Permission> listByRole(String roleName);

}
