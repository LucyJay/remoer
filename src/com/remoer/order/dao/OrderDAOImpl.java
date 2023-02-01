package com.remoer.order.dao;

import java.util.ArrayList;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
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
				if(Main.isAdmin()) {
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
		// TODO Auto-generated method stub
		return null;
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
