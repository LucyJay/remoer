package com.remoer.member.dao;

import com.remoer.member.vo.LoginVO;

public interface MemberDAO {
	
	public LoginVO login(LoginVO vo) throws Exception;

}
