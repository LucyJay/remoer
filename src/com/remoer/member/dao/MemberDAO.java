package com.remoer.member.dao;

import com.remoer.member.vo.LoginVO;

public interface MemberDAO {
	
	public LoginVO login(LoginVO vo) throws Exception;
	
	public boolean checkId(String id) throws Exception;
	
	public Integer join(LoginVO vo) throws Exception;
	
	public String findId(LoginVO vo) throws Exception;
	
	public Integer updatePw(LoginVO vo) throws Exception;

}
