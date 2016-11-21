package com.caozj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caozj.dao.BussDao;
import com.caozj.framework.util.groovy.GroovyUtil;
import com.caozj.model.Buss;
import com.caozj.service.BussService;

@Service
@Transactional
public class BussServiceImpl implements BussService {

	@Autowired
	private BussDao bussDao;

	@Override
	public void add(Buss buss) {
		bussDao.add(buss);
	}

	@Override
	public void update(Buss buss) {
		bussDao.update(buss);
	}

	@Override
	public void delete(String name) {
		bussDao.delele(name);
	}

	@Override
	public Buss get(String name) {
		return bussDao.get(name);
	}

	@Override
	public List<Buss> listAll() {
		return bussDao.listAll();
	}

	@Override
	public Object execute(Map<String, String[]> params, String name) {
		Buss buss = this.get(name);
		if (buss == null) {
			throw new RuntimeException("脚本不存在");
		}
		String script = buss.getScript();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("params", params);
		return GroovyUtil.executeScript(script, param);
	}

	@Override
	public void batchDelete(List<String> nameList) {
		for (String name : nameList) {
			this.delete(name);
		}
	}
}
