package com.remoer.notice.service;

import com.remoer.main.ServiceInterface;
import com.remoer.notice.dao.NoticeDAO;
import com.remoer.notice.dao.NoticeDAOImpl;

public class NoticeListServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.list((String) obj);
	}

}
