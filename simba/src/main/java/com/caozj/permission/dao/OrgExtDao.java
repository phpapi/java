package com.caozj.permission.dao;

import java.util.List;

import com.caozj.permission.model.OrgExt;

/**
 * 
 * 
 * @author caozj
 * 
 */
public interface OrgExtDao {

	List<String> showAllColumns();

	void addColumn(String column);

	OrgExt get(int orgID);

	void add(OrgExt orgExt);

	void update(OrgExt orgExt);
}
