package com.remoer.order.service;

import com.remoer.main.ServiceInterface;
import com.remoer.order.dao.OrderDAO;
import com.remoer.order.dao.OrderDAOImpl;
import com.remoer.order.vo.OrderVO;

public class OrderServiceImpl implements ServiceInterface {
	@Override
	public Object service(Object obj) throws Exception {
		OrderDAO dao = new OrderDAOImpl();
		OrderVO vo = (OrderVO) obj;
		if (dao.minusQuantity(vo) == 1) {
			if (dao.order(vo) == 1) {
				Long no = dao.findOrderNo(vo.getId());
				if (no != null) {
					vo.setNo(dao.findOrderNo(vo.getId()));
					return dao.orderGoods(vo);
				}
			}
			return 0;
		}
		return -1;
	}
}
