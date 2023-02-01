package com.remoer.order.service;

import com.remoer.main.ServiceInterface;
import com.remoer.order.dao.OrderDAO;
import com.remoer.order.dao.OrderDAOImpl;
import com.remoer.order.vo.OrderVO;

public class OrderUpdateDlvServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		OrderDAO dao = new OrderDAOImpl();
		return dao.updateDlv((OrderVO)obj);
	}

}
