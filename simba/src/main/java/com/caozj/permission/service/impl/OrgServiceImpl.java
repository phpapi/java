package com.caozj.permission.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.permission.dao.OrgDao;
import com.caozj.permission.dao.OrgExtDao;
import com.caozj.permission.model.Org;
import com.caozj.permission.model.OrgExt;
import com.caozj.permission.model.OrgExtDesc;
import com.caozj.permission.service.OrgService;

/**
 * 
 * 
 * @author caozj
 * 
 */
@Service
@Transactional
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;

	@Autowired
	private OrgExtDao orgExtDao;

	@Value("${org.ext}")
	private String orgExt;

	@Override
	public int add(Org org) {
		return orgDao.add(org);
	}

	@Override
	public void delete(int id) {
		if (orgDao.countBy("parentID", id) > 0) {
			throw new RuntimeException("机构下有子机构，不能删除，请先删除子机构");
		}
		orgDao.delete(id);
	}

	@Override
	public Org get(int id) {
		return orgDao.get(id);
	}

	@Override
	public List<Org> page(Pager page) {
		return orgDao.page(page);
	}

	@Override
	public int count() {
		return orgDao.count();
	}

	@Override
	public List<Org> listAll() {
		return orgDao.listAll();
	}

	@Override
	public void update(Org org) {
		orgDao.update(org);
	}

	@Override
	public void batchDelete(List<Integer> idList) {
		for (Integer id : idList) {
			this.delete(id);
		}
	}

	@Override
	public Org getBy(String field, Object value) {
		return orgDao.getBy(field, value);
	}

	@Override
	public Org getByAnd(String field1, Object value1, String field2, Object value2) {
		return orgDao.getByAnd(field1, value1, field2, value2);
	}

	@Override
	public Org getByOr(String field1, Object value1, String field2, Object value2) {
		return orgDao.getByOr(field1, value1, field2, value2);
	}

	@Override
	public List<Org> listBy(String field, Object value) {
		return orgDao.listBy(field, value);
	}

	@Override
	public List<Org> listByAnd(String field1, Object value1, String field2, Object value2) {
		return orgDao.listByAnd(field1, value1, field2, value2);
	}

	@Override
	public List<Org> listByOr(String field1, Object value1, String field2, Object value2) {
		return orgDao.listByOr(field1, value1, field2, value2);
	}

	@Override
	public List<Org> pageBy(String field, Object value, Pager page) {
		return orgDao.pageBy(field, value, page);
	}

	@Override
	public List<Org> pageByAnd(String field1, Object value1, String field2, Object value2, Pager page) {
		return orgDao.pageByAnd(field1, value1, field2, value2, page);
	}

	@Override
	public List<Org> pageByOr(String field1, Object value1, String field2, Object value2, Pager page) {
		return orgDao.pageByOr(field1, value1, field2, value2, page);
	}

	@Override
	public void checkAndCreateOrgExt() {
		if (StringUtils.isBlank(orgExt)) {
			return;
		}
		List<String> existColumns = orgExtDao.showAllColumns();
		String[] ext = orgExt.trim().split(",");
		for (String column : ext) {
			String[] kv = column.split(":");
			String key = kv[0];
			String value = kv[1];
			OrgExtDesc.put(key, value);
			if (!existColumns.contains(key)) {
				orgExtDao.addColumn(key);
			}
		}
	}

	@Override
	public List<Org> listChildren(int parentID) {
		List<Org> children = orgDao.listBy("parentID", parentID);
		children.forEach((org) -> {
			int childrenCount = orgDao.countBy("parentID", org.getId());
			if (childrenCount > 0) {
				org.setLeaf(false);
				org.setState("closed");
			} else {
				org.setLeaf(true);
				org.setState("open");
			}
		});
		return children;
	}

	@Override
	public void add(Org org, OrgExt orgExt) {
		int id = orgDao.add(org);
		org.setId(id);
		orgExt.setId(id);
		orgExtDao.add(orgExt);
	}

	@Override
	public OrgExt getOrgExt(int id) {
		return orgExtDao.get(id);
	}

	@Override
	public void update(Org org, OrgExt orgExt) {
		orgDao.update(org);
		orgExtDao.update(orgExt);
	}
}
