package com.caozj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.caozj.dao.ChatRecordDao;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.ChatRecord;
import com.caozj.service.ChatRecordService;

/**
 * 
 * 
 * @author caozj
 * 
 */
@Service
@Transactional
public class ChatRecordServiceImpl implements ChatRecordService {

	@Autowired
	private ChatRecordDao chatRecordDao;

	@Override
	public void add(ChatRecord chatRecord) {
		chatRecordDao.add(chatRecord);
	}

	@Override
	public void delete(int id) {
		chatRecordDao.delete(id);
	}

	@Override
	public ChatRecord get(int id) {
		return chatRecordDao.get(id);
	}

	@Override
	public List<ChatRecord> page(Pager page) {
		return chatRecordDao.page(page);
	}

	@Override
	public int count() {
		return chatRecordDao.count();
	}

	@Override
	public List<ChatRecord> listAll() {
		return chatRecordDao.listAll();
	}

	@Override
	public void update(ChatRecord chatRecord) {
		chatRecordDao.update(chatRecord);
	}
	
	@Override
	public void batchDelete(List<Integer> idList) {
		for (Integer id : idList) {
			this.delete(id);
		}
	}
}
