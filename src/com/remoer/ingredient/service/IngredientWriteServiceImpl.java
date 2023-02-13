package com.remoer.ingredient.service;

import com.remoer.ingredient.dao.IngredientDAO;
import com.remoer.ingredient.dao.IngredientDAOImpl;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.ServiceInterface;

public class IngredientWriteServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		IngredientDAO dao = new IngredientDAOImpl();
		IngredientVO vo = (IngredientVO) obj;
		Long checkNo = dao.checkIng(vo.getName());
		if (checkNo == 0L) {
			return dao.write(vo);
		} else if (checkNo == -1L) {
			return -1;
		} else {
			vo.setNo(checkNo);
			return dao.update(vo);
		}
	}

}
