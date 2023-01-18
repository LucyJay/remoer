package com.remoer.recipe.service;

import java.util.List;

import com.remoer.ingredient.dao.IngredientDAO;
import com.remoer.ingredient.dao.IngredientDAOImpl;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.ServiceInterface;
import com.remoer.recipe.dao.RecipeDAO;
import com.remoer.recipe.dao.RecipeDAOImpl;
import com.remoer.recipe.vo.RecipeVO;

public class RecipeUpdateServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		RecipeDAO dao = new RecipeDAOImpl();
		IngredientDAO daoI = new IngredientDAOImpl();
		Object[] objs = (Object[]) obj;
		RecipeVO recipeVO = (RecipeVO) objs[0];
		dao.update(recipeVO);
		if ((boolean) objs[1]) {
			dao.deleteI_R(recipeVO.getNo());
			List<IngredientVO> ingList = recipeVO.getIngreList();
			for (IngredientVO ingVO : ingList) {
				String ing = ingVO.getName();
				if (daoI.checkIng(ing)==0L) {
					dao.writeIng(ing);
				}
				Long ingNo = dao.findIngNo(ing);
				dao.writeI_R(ingNo, recipeVO.getNo());
			}
		}
		return 1;
	}

}
