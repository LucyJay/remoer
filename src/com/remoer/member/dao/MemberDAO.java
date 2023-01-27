package com.remoer.member.dao;

import java.sql.Date;
import java.util.List;

import com.remoer.member.vo.LoginVO;

public interface MemberDAO {

	public LoginVO login(LoginVO vo) throws Exception;

	public boolean checkId(String id) throws Exception;

	public Integer join(LoginVO vo) throws Exception;

	public String findId(LoginVO vo) throws Exception;

	public Integer updatePw(LoginVO vo) throws Exception;

	public Integer updateCondate(String id) throws Exception;

	public boolean idExists(String id) throws Exception;

	public LoginVO view(String id) throws Exception;

	public Integer updateInfo(LoginVO vo) throws Exception;

	public Integer updateStatus(LoginVO vo) throws Exception;

	public Integer updateGrade(LoginVO vo) throws Exception;

	public Integer wakeUp(LoginVO vo) throws Exception;

	public List<LoginVO> list(String type) throws Exception;

	public Integer rest(Date date) throws Exception;

}
