package com.remoer.order.service;

import com.remoer.main.ServiceInterface;
import com.remoer.order.dao.OrderDAO;
import com.remoer.order.dao.OrderDAOImpl;

public class OrderCancelServicelmpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		OrderDAO dao = new OrderDAOImpl();
		return dao.cancel((Long)obj);
	}

}
