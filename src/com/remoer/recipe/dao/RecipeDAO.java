package com.remoer.recipe.dao;

import java.util.List;

import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.recipe.vo.RecipeVO;
import com.remoer.recipe.vo.ReplyVO;
import com.remoer.recipe.vo.StarVO;

public interface RecipeDAO {
	
	//리스트
	public List<RecipeVO> list(boolean isBest) throws Exception;
	
	public List<RecipeVO> myList(String id) throws Exception;
	
//	//글보기
//	public RecipeVO view(Long no) throws Exception;
//	public IngredientVO[] viewTag(Long no) throws Exception;
//	public ReplyVO[] viewReply(Long no) throws Exception;
//	
//	//글쓰기
//	public Integer write(RecipeVO rec, IngredientVO[] ingr) throws Exception;
//	
//	//별점 등록, 취소
//	public Integer star(StarVO vo) throws Exception;
//	public Integer deStar(StarVO vo) throws Exception;
//	
//	//댓글 등록, 수정, 삭제
//	public Integer reply(ReplyVO vo) throws Exception;
//	public Integer updatereply(ReplyVO vo) throws Exception;
//	public Integer deleteReply(Long no) throws Exception;
	
	
	
	
	

}
