package com.remoer.ingredient.dao;

import java.util.ArrayList;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.ingredient.vo.IngredientVO;

public class IngredientDAOImpl extends DAO implements IngredientDAO {

	@Override
	public List<IngredientVO> list(boolean soldout) throws Exception {
		try {
			List<IngredientVO> list = null;
			con = DB.getConnection();
			String sql = "";
			if (soldout)
				sql = "SELECT no, name, price, quantity FROM ingredient WHERE quantity IS NOT NULL ORDER BY no";
			else
				sql = "SELECT no, name, price, quantity FROM ingredient WHERE quantity IS NOT NULL AND quantity > 0 ORDER BY no";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				IngredientVO vo = new IngredientVO();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setPrice(rs.getInt(3));
				vo.setQuantity(rs.getInt(4));
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
	public IngredientVO view(Long no) throws Exception {
		try {
			IngredientVO vo = null;
			con = DB.getConnection();
			String sql = "SELECT no, name, description, price, quantity FROM ingredient WHERE no = ? AND quantity IS NOT NULL";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new IngredientVO();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setDescription(rs.getString(3));
				vo.setPrice(rs.getInt(4));
				vo.setQuantity(rs.getInt(5));
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
	public Long checkIng(String name) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT no, quantity FROM ingredient WHERE name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getObject(2) == null)
					return rs.getLong(1);
				else
				return -1L;
			}
			return 0L;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer setQuantity(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE ingredient SET quantity = 0 where no = ?";
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
	public Integer update(IngredientVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE ingredient SET description = ?, price = ?, quantity = quantity + ? where no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getDescription());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setInt(3, vo.getQuantity());
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
	public Integer write(IngredientVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO ingredient VALUES (ingredient_seq.nextval, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getDescription());
			pstmt.setInt(3, vo.getPrice());
			pstmt.setInt(4, vo.getQuantity());
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
			String sql = "UPDATE ingredient SET description =  NULL, price = null, quantity = NULL WHERE no = ?";
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

}
