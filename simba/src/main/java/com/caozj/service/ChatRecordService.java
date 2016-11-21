package  com.caozj.service;

import java.util.List;

import com.caozj.framework.util.jdbc.Pager;
import com.caozj.model.ChatRecord;

/**
 *
 * 
 * @author caozj
 * 
 */
public interface ChatRecordService {

	void add(ChatRecord chatRecord);

	void update(ChatRecord chatRecord);

	void delete(int id);

	List<ChatRecord> listAll();

	int count();
	
	List<ChatRecord> page(Pager page);

	ChatRecord get(int id);
	
	void batchDelete(List<Integer> idList);

}
