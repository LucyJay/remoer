package com.remoer.qna.service;

import com.remoer.main.ServiceInterface;
import com.remoer.qna.dao.QnaDAO;
import com.remoer.qna.dao.QnaDAOImpl;
import com.remoer.qna.vo.QnaVO;

public class QnaUpdateAnswerServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		QnaDAO dao = new QnaDAOImpl();
		return dao.updateA((QnaVO) obj);
	}

}
