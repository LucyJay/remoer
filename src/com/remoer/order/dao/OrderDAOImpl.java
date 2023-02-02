package com.remoer.order.dao;

import java.util.ArrayList;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.ingredient.vo.GoodsVO;
import com.remoer.main.Main;
import com.remoer.order.vo.OrderVO;

public class OrderDAOImpl extends DAO implements OrderDAO {

	@Override
	public Integer order(OrderVO vo) throws Exception {
		return null;
	}

	@Override
	public List<OrderVO> list(String id) throws Exception {
		try {
			List<OrderVO> list = null;
			con = DB.getConnection();
			String sql = "";
			if (Main.isAdmin()) {
				sql = "SELECT no, id, to_char(ord_date, 'yyyy-mm-dd') ord_date, status, to_char(delivery_date, 'yyyy-mm-dd') delivery_date FROM ord ORDER BY no desc";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "SELECT no, ord_date, status, delivery_date FROM ord WHERE id = ? ORDER BY no desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				OrderVO vo = new OrderVO();
				vo.setNo(rs.getLong("no"));
				if (Main.isAdmin()) {
					vo.setId(rs.getString("id"));
				}
				vo.setOrder_date(rs.getString("ord_date"));
				vo.setStatus(rs.getString("status"));
				vo.setDlv_date(rs.getString("delivery_date"));
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
	public OrderVO view(Long no) throws Exception {
		try {
			OrderVO vo = null;
			con = DB.getConnection();
			String sql = "SELECT o.no, m.name, o.address, o.tel, o.ord_date, o.status, o.delivery_date FROM member m, ord o WHERE o.no = ? AND m.id=o.id";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new OrderVO();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setAddress(rs.getString(3));
				vo.setTel(rs.getString(4));
				vo.setOrder_date(rs.getString(5));
				vo.setStatus(rs.getString(6));
				vo.setDlv_date(rs.getString(7));

				sql = "SELECT i.name, i.price, i.quantity FROM ord_list l, ingredient i WHERE l.ord_no=? AND i.no=l.ingre_no";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, no);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					GoodsVO gvo = new GoodsVO();
					gvo.setGoods_name(rs.getString(1));
					gvo.setPrice(rs.getInt(2));
					gvo.setQuantity(rs.getInt(3));
					vo.getList().add(gvo);
				}
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer updateAddress(OrderVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer cancel(Long no) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateDlv(OrderVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
