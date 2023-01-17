package com.remoer.recipe.service;

import com.remoer.main.ServiceInterface;
import com.remoer.recipe.dao.RecipeDAO;
import com.remoer.recipe.dao.RecipeDAOImpl;
import com.remoer.recipe.vo.RecipeVO;

public class RecipeCheckWriterServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		RecipeDAO dao = new RecipeDAOImpl();
		return dao.checkWriter((RecipeVO) obj);
	}

}
