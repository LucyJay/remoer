package com.remoer.cart.dao;

import java.util.List;

import com.remoer.cart.vo.CartVO;

public interface CartDAO {

	public List<CartVO> list(String id) throws Exception;
	
	public CartVO view(Long no) throws Exception;

	public Integer updateQuantity(CartVO vo) throws Exception;

	public Integer delete(Long no) throws Exception;

	public Integer deleteAll(String id) throws Exception;

}
