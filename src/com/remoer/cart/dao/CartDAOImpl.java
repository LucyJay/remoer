package com.remoer.cart.dao;

import java.util.ArrayList;
import java.util.List;

import com.remoer.cart.vo.CartVO;
import com.remoer.dao.DAO;
import com.remoer.dao.DB;

public class CartDAOImpl extends DAO implements CartDAO {

	@Override
	public List<CartVO> list(String id) throws Exception {
		try {
			List<CartVO> list = null;
			con = DB.getConnection();
			String sql = "SELECT c.no, i.name, i.price, c.quantity, c.cart_date FROM cart c, ingredient i WHERE c.id = ? AND c.ingredient = i.no ORDER BY no desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				CartVO vo = new CartVO();
				vo.setNo(rs.getLong(1));
				vo.setGoods_name(rs.getString(2));
				vo.setPrice(rs.getInt(3));
				vo.setQuantity(rs.getInt(4));
				vo.setCart_date(rs.getString(5));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public CartVO view(Long no) throws Exception {
		try {
			CartVO vo = null;
			con = DB.getConnection();
			String sql = "SELECT id, ingredient, quantity FROM cart WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			vo = new CartVO();
			vo.setId(rs.getString(1));
			vo.setGoods_no(rs.getLong(2));
			vo.setQuantity(rs.getInt(3));
			return vo;
		} catch (

		Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer updateQuantity(CartVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE cart SET quantity = ? where no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getQuantity());
			pstmt.setString(2, vo.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer delete(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM cart where no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer deleteAll(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM cart where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

}
