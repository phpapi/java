package com.caozj.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.caozj.dao.ChatRecordDao;
import com.caozj.framework.util.jdbc.Jdbc;
import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.ChatRecord;

/**
 * 
 * 
 * @author caozj
 *  
 */
@Repository
public class ChatRecordDaoImpl implements ChatRecordDao {

	@Autowired
	private Jdbc jdbc;

	private static final String table = "chatRecord";

	@Override
	public void add(ChatRecord chatRecord) {
		String sql = "insert into " + table + "( fromAccount, fromName, toAccount, toName, content, sendTime) values(?,?,?,?,?,?)";
		jdbc.updateForBoolean(sql, chatRecord.getFromAccount(),chatRecord.getFromName(),chatRecord.getToAccount(),chatRecord.getToName(),chatRecord.getContent(),chatRecord.getSendTime());
	}

	@Override
	public void update(ChatRecord chatRecord) {
		String sql = "update " + table + " set  fromAccount = ? , fromName = ? , toAccount = ? , toName = ? , content = ? , sendTime = ?  where id = ?  ";
		jdbc.updateForBoolean(sql,chatRecord.getFromAccount(),chatRecord.getFromName(),chatRecord.getToAccount(),chatRecord.getToName(),chatRecord.getContent(),chatRecord.getSendTime(), chatRecord.getId());
	}

	@Override
	public void delete(int id) {
		String sql = "delete from " + table + " where id = ? ";
		jdbc.updateForBoolean(sql, id);
	}

	@Override
	public List<ChatRecord> page(Pager page) {
		String sql = "select * from " + table;
		return jdbc.queryForPage(sql, ChatRecord.class, page);
	}

	@Override
	public List<ChatRecord> listAll(){
		String sql = "select * from " + table;
		return jdbc.queryForList(sql, ChatRecord.class);
	}

	@Override
	public int count(){
		String sql = "select count(*) from " + table;
		return jdbc.queryForInt(sql); 
	}

	@Override
	public ChatRecord get(int id) {
		String sql = "select * from " + table + " where id = ? ";
		return jdbc.query(sql, ChatRecord.class, id);
	}

}
