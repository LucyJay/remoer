package com.remoer.recipe.service;

import com.remoer.main.ServiceInterface;
import com.remoer.recipe.dao.RecipeDAO;
import com.remoer.recipe.dao.RecipeDAOImpl;

public class RecipeDeleteServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		RecipeDAO dao = new RecipeDAOImpl();
		return dao.delete((Long) obj);
	}

}
