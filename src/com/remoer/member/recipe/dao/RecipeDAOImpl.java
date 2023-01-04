package com.remoer.member.recipe.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.member.ingredient.vo.IngredientVO;
import com.remoer.member.recipe.vo.RecipeVO;
import com.remoer.member.recipe.vo.ReplyVO;
import com.remoer.member.recipe.vo.StarVO;

public class RecipeDAOImpl extends DAO implements RecipeDAO {

	@Override
	public List<RecipeVO> list(boolean isBest) throws Exception {

		List<RecipeVO> list = null;
		try {
			con = DB.getConnection();
			String sql = "";
			if (isBest) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -1);
				Date d = new Date(c.getTimeInMillis());
				DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
				String s = df.format(d);
				sql = "SELECT r.no, r.title, r.writer, r.write_date, (SELECT count(star) FROM star GROUP BY recipe) scnt, (SELECT avg(star) FROM star GROUP BY recipe) savg, (SELECT count(no) FROM reply GROUP BY recipe) crep FROM recipe r WHERE write_date <= "
						+ s + " ORDER BY no DESC";
			} else {
				sql = "SELECT r.no, r.title, r.writer, r.write_date, (SELECT count(star) FROM star GROUP BY recipe) scnt, (SELECT avg(star) FROM star GROUP BY recipe) savg, (SELECT count(no) FROM reply GROUP BY recipe) crep FROM recipe r ORDER BY no DESC";
			}
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				RecipeVO vo = new RecipeVO();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setWriter(rs.getString(3));
				vo.setWrite_date(rs.getString(4));
				vo.setCntStar(rs.getLong(5));
				vo.setAvrStar(rs.getDouble(6));
				vo.setCntReply(rs.getLong(7));
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
	public List<RecipeVO> myList(String id) throws Exception {
		List<RecipeVO> list = null;
		try {
			con = DB.getConnection();
			String sql = "SELECT r.no, r.title, r.writer, r.write_date, (SELECT count(star) FROM star GROUP BY recipe) scnt, (SELECT avg(star) FROM star GROUP BY recipe) savg, (SELECT count(no) FROM reply GROUP BY recipe) crep FROM recipe r WHERE writer = ? ORDER BY no DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				RecipeVO vo = new RecipeVO();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setWriter(rs.getString(3));
				vo.setWrite_date(rs.getString(4));
				vo.setCntStar(rs.getLong(5));
				vo.setAvrStar(rs.getDouble(6));
				vo.setCntReply(rs.getLong(7));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

//	@Override
//	public RecipeVO view(Long no) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public IngredientVO[] viewTag(Long no) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public ReplyVO[] viewReply(Long no) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public Integer write(RecipeVO rec, IngredientVO[] ingr) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer star(StarVO vo) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public Integer deStar(StarVO vo) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public Integer reply(ReplyVO vo) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public Integer updatereply(ReplyVO vo) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}
//
//	@Override
//	public Integer deleteReply(Long no) throws Exception {
//		try {
//			con = DB.getConnection();
//			String sql = "";
//			pstmt = con.prepareStatement(sql);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			close();
//		}
//	}

}
