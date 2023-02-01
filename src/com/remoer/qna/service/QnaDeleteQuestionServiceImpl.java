package com.remoer.qna.service;

import com.remoer.main.ServiceInterface;
import com.remoer.qna.dao.QnaDAO;
import com.remoer.qna.dao.QnaDAOImpl;

public class QnaDeleteQuestionServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		QnaDAO dao = new QnaDAOImpl();
		return dao.deleteQ((Long) obj);
	}

}
