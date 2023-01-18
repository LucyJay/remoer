package com.remoer.ingredient.service;

import com.remoer.ingredient.dao.IngredientDAO;
import com.remoer.ingredient.dao.IngredientDAOImpl;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.ServiceInterface;

public class IngredientUpdateServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		IngredientDAO dao = new IngredientDAOImpl();
		return dao.update((IngredientVO) obj);
	}

}
