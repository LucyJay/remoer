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
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO ord (no, id, name, address, tel, totalPrice) VALUES (ord_seq.nextval, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getAddress());
			pstmt.setString(4, vo.getTel());
			pstmt.setInt(5,  vo.getTotalPrice());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Long findOrderNo(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT max(no) FROM ord WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getLong(1);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer orderGoods(OrderVO vo) throws Exception {
		try {
			con = DB.getConnection();
			List<GoodsVO> list = vo.getList();
			String sql = "INSERT INTO ord_list VALUES (?, ?, ?)";
			for (GoodsVO goods : list) {
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, vo.getNo());
				pstmt.setLong(2, goods.getGoods_no());
				pstmt.setInt(3, goods.getQuantity());
				if (pstmt.executeUpdate() != 1) {
					return 0;
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer minusQuantity(OrderVO vo) throws Exception {
		try {
			con = DB.getConnection();
			List<GoodsVO> list = vo.getList();
			String sql = "UPDATE ingredient SET quantity = quantity - ? WHERE no = ?";
			for (GoodsVO goods : list) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, goods.getQuantity());
				pstmt.setLong(2, goods.getGoods_no());
				if (pstmt.executeUpdate() != 1) {
					return 0;
				}
			}
			return 1;
		} catch (Exception e) {
			return 0;
		} finally {
			close();
		}
	}

	@Override
	public List<OrderVO> list(String id) throws Exception {
		try {
			List<OrderVO> list = null;
			con = DB.getConnection();
			String sql = "";
			if (Main.isAdmin()) {
				sql = "SELECT no, id, to_char(ord_date, 'yyyy-mm-dd') ord_date, status, to_char(delivery_date, 'yyyy-mm-dd') delivery_date, totalPrice FROM ord ORDER BY no desc";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "SELECT no, to_char(ord_date, 'yyyy-mm-dd') ord_date, status, to_char(delivery_date, 'yyyy-mm-dd') delivery_date, totalPrice FROM ord WHERE id = ? ORDER BY no desc";
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
				vo.setTotalPrice(rs.getInt("totalPrice"));
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
			String sql = "SELECT no, id, name, address, tel, ord_date, status, delivery_date, totalPrice FROM ord WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new OrderVO();
				vo.setNo(rs.getLong(1));
				vo.setId(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setAddress(rs.getString(4));
				vo.setTel(rs.getString(5));
				vo.setOrder_date(rs.getString(6));
				vo.setStatus(rs.getString(7));
				vo.setDlv_date(rs.getString(8));
				vo.setTotalPrice(rs.getInt(9));

				sql = "SELECT i.name, i.price, l.quantity FROM ord_list l, ingredient i WHERE l.ord_no = ? AND i.no = l.ingre_no";
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, no);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					GoodsVO gvo = new GoodsVO();
					gvo.setGoods_name(rs.getString(1));
					gvo.setPrice(rs.getInt(2));
					gvo.setQuantity(rs.getInt(3));
					gvo.setTotalPrice(gvo.totalPrice());
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
		try {
			con = DB.getConnection();
			String sql = "UPDATE ord SET address = ?, name = ?, tel = ? where no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getAddress());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getTel());
			pstmt.setLong(4, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer cancel(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE ord SET status = '구매취소' where no = ?";
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
	public Integer updateDlv(OrderVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "";
			if (vo.getStatus().equals("배송완료")) {
				sql = "UPDATE ord SET status = ?, delivery_date = sysdate where no = ?";
			} else {
				sql = "UPDATE ord SET status = ? where no = ?";
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getStatus());
			pstmt.setLong(2, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

}
