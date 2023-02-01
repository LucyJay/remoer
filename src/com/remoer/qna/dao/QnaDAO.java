package com.remoer.qna.dao;

import java.util.List;

import com.remoer.qna.vo.FaqVO;
import com.remoer.qna.vo.QnaVO;

public interface QnaDAO {

	public List<FaqVO> listFaq() throws Exception;

	public List<QnaVO> listQna(String id) throws Exception;

	public QnaVO view(Long no) throws Exception;

	public Integer writeQ(QnaVO vo) throws Exception;

	public Integer updateQ(QnaVO vo) throws Exception;

	public Integer deleteQ(Long no) throws Exception;

	public Integer writeA(QnaVO vo) throws Exception;

	public Integer updateA(QnaVO vo) throws Exception;

	public Integer deleteA(Long no) throws Exception;

	public FaqVO viewFaq(Long no) throws Exception;

	public Integer writeFaq(FaqVO vo) throws Exception;

	public Integer updateFaq(FaqVO vo) throws Exception;

	public Integer deleteFaq(Long no) throws Exception;
}
