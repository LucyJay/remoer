package com.remoer.notice.service;

import com.remoer.main.ServiceInterface;
import com.remoer.notice.dao.NoticeDAO;
import com.remoer.notice.dao.NoticeDAOImpl;

public class NoticeDeleteServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.delete((Long) obj);
	}

}
