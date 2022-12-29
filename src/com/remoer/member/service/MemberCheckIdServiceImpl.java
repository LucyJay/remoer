package com.remoer.member.service;

import com.remoer.main.ServiceInterface;
import com.remoer.member.dao.MemberDAO;
import com.remoer.member.dao.MemberDAOImpl;

public class MemberCheckIdServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		MemberDAO dao = new MemberDAOImpl();
		return dao.checkId((String) obj);
	}

}
