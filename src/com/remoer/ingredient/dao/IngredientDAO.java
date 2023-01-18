package com.remoer.ingredient.dao;

import java.util.List;

import com.remoer.ingredient.vo.IngredientVO;

public interface IngredientDAO {
	
	//리스트
		public List<IngredientVO> list(boolean soldout) throws Exception;
		
	//보기
		public IngredientVO view(Long no) throws Exception;

	//등록, 수정
		public Long checkIng(String name) throws Exception;
		public Integer update(IngredientVO vo) throws Exception;
		public Integer write(IngredientVO vo) throws Exception;

	//삭제
		public Integer delete(Long no) throws Exception;

}
