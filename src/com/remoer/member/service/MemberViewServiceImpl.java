package com.remoer.member.service;

import com.remoer.main.ServiceInterface;
import com.remoer.member.dao.MemberDAO;
import com.remoer.member.dao.MemberDAOImpl;

public class MemberViewServiceImpl implements ServiceInterface  {

	@Override
	public Object service(Object obj) throws Exception {
		MemberDAO  dao = new MemberDAOImpl();
		
		return dao.view((String)obj);
	}

}
