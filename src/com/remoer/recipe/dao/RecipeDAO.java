package com.remoer.recipe.dao;

import java.util.List;

import com.remoer.recipe.vo.RecipeVO;
import com.remoer.recipe.vo.ReplyVO;
import com.remoer.recipe.vo.StarVO;

public interface RecipeDAO {
	
	//리스트
	public List<RecipeVO> list(boolean isBest) throws Exception;
	
	public List<RecipeVO> myList(String id) throws Exception;
	
//	//글보기
	public RecipeVO view(Long no) throws Exception;

//	
//	//글쓰기
	public Integer write(RecipeVO rec) throws Exception;
	public boolean checkIng(String name) throws Exception;
	public Integer writeIng(String name) throws Exception;
	public Long findRecNo() throws Exception;
	public Long findIngNo(String name) throws Exception;
	public Integer writeI_R(Long in, Long rn) throws Exception;
	
//	//별점 등록, 취소
	public Integer star(StarVO vo) throws Exception;
	public Integer deStar(StarVO vo) throws Exception;
//	
//	//댓글 등록, 수정, 삭제
	public Integer reply(ReplyVO vo) throws Exception;
	public boolean checkWriter(ReplyVO vo) throws Exception;
	public Integer updateReply(ReplyVO vo) throws Exception;
	public Integer deleteReply(Long no) throws Exception;
	
	//글 수정
	public boolean checkWriter(RecipeVO vo) throws Exception;
	public Integer deleteI_R(Long rn) throws Exception;
	public Integer update(RecipeVO vo) throws Exception;
	//글 삭제
	public Integer delete(Long no) throws Exception;
	
	

}
