package com.remoer.order.dao;

import java.util.List;

import com.remoer.order.vo.OrderVO;

public interface OrderDAO {

	public Integer order(OrderVO vo) throws Exception;

	public List<OrderVO> list(String id) throws Exception;

	public OrderVO view(Long no) throws Exception;

	public Integer updateAddress(OrderVO vo) throws Exception;

	public Integer cancel(Long no) throws Exception;

	public Integer updateDlv(OrderVO vo) throws Exception;

}
