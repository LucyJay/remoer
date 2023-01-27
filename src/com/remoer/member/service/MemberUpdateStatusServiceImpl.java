package com.remoer.member.service;

import com.remoer.main.ServiceInterface;
import com.remoer.member.dao.MemberDAO;
import com.remoer.member.dao.MemberDAOImpl;
import com.remoer.member.vo.LoginVO;

public class MemberUpdateStatusServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		MemberDAO dao = new MemberDAOImpl();
		
		return dao.updateStatus((LoginVO )obj);
	}

}
