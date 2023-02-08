package com.remoer.recipe.service;

import java.util.List;

import com.remoer.ingredient.dao.IngredientDAO;
import com.remoer.ingredient.dao.IngredientDAOImpl;
import com.remoer.main.ServiceInterface;
import com.remoer.recipe.dao.RecipeDAO;
import com.remoer.recipe.dao.RecipeDAOImpl;
import com.remoer.recipe.vo.RecipeVO;

public class RecipeWriteServiceImpl implements ServiceInterface {

	@SuppressWarnings("unchecked")
	@Override
	public Object service(Object obj) throws Exception {
		RecipeDAO dao = new RecipeDAOImpl();
		IngredientDAO daoI = new IngredientDAOImpl();
		Object[] objs = (Object[]) obj;
		dao.write((RecipeVO) objs[0]);
		Long recNo = dao.findRecNo();
		List<String> ingList = (List<String>) objs[1];
		for (String ing : ingList) {
			if (daoI.checkIng(ing)==0L) {
				dao.writeIng(ing);
			}
			Long ingNo = dao.findIngNo(ing);
			dao.writeI_R(ingNo, recNo);
		}

		return 1;
	}

}
