package com.remoer.notice.dao;

import java.util.List;

import com.remoer.notice.vo.NoticeVO;

public interface NoticeDAO {
	
	public List<NoticeVO> list() throws Exception;
	
	public NoticeVO view(Long no) throws Exception;
	
	public Integer write(NoticeVO vo) throws Exception;
	
	public Integer update(NoticeVO vo) throws Exception;
	
	public Integer delete(NoticeVO vo) throws Exception;
	
}
