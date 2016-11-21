package com.caozj.permission.dao;

import java.util.List;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.permission.model.User;

public interface UserDao {

	void add(User user);

	void update(User user);

	void delete(String account);

	void updateName(String account, String name);

	void updatePwd(String account, String pwd);

	User get(String account);

	List<User> page(Pager page);

	boolean checkUser(String account, String pwd);

	List<User> listAll();

}
