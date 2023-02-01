package com.remoer.order.service;

import com.remoer.main.ServiceInterface;
import com.remoer.order.dao.OrderDAO;
import com.remoer.order.dao.OrderDAOImpl;

public class OrderListServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		OrderDAO dao = new OrderDAOImpl();
		return dao.list((String)obj);
	}

}
