package com.remoer.cart.service;

import com.remoer.cart.dao.CartDAO;
import com.remoer.cart.dao.CartDAOImpl;
import com.remoer.main.ServiceInterface;

public class CartViewServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		CartDAO dao = new CartDAOImpl();
		return dao.view((Long)obj);
	}

}
