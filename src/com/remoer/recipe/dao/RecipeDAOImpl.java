package com.remoer.recipe.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.recipe.vo.RecipeVO;
import com.remoer.recipe.vo.ReplyVO;
import com.remoer.recipe.vo.StarVO;

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
				sql = "SELECT r.no, r.title, m.nickname, to_char(r.write_date,'yyyy-mm-dd') write_date, (SELECT count(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) scnt, "
						+ " (SELECT avg(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) savg, (SELECT count(no) FROM reply WHERE reply.recipe = r.no GROUP BY recipe) crep "
						+ " FROM recipe r, member m WHERE write_date >= ? AND r.writer = m.id ORDER BY no DESC";
				pstmt = con.prepareStatement(sql);
				pstmt.setDate(1, d);
			} else {
				sql = "SELECT r.no, r.title, m.nickname, to_char(r.write_date,'yyyy-mm-dd') write_date, (SELECT count(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) scnt, "
						+ " (SELECT avg(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) savg, (SELECT count(no) FROM reply WHERE reply.recipe = r.no GROUP BY recipe) crep "
						+ " FROM recipe r, member m WHERE r.writer = m.id ORDER BY no DESC";
				pstmt = con.prepareStatement(sql);
			}
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
	public List<RecipeVO> myList(String id) throws Exception {
		List<RecipeVO> list = null;
		System.out.println(id);
		try {
			con = DB.getConnection();
			String sql = "SELECT r.no, r.title, m.nickname, to_char(r.write_date, 'yyyy-mm-dd') write_date, (SELECT count(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) scnt, "
					+ " (SELECT avg(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) savg, (SELECT count(no) FROM reply WHERE reply.recipe = r.no GROUP BY recipe) crep "
					+ " FROM recipe r, member m WHERE writer = ? AND r.writer = m.id ORDER BY no DESC";
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
	public RecipeVO view(Long no) throws Exception {
		RecipeVO vo = null;
		try {
			con = DB.getConnection();
			String sql = "SELECT r.no, r.title, r.content,  m.nickname, to_char(r.write_date, 'yyyy-mm-dd') write_date, to_char(r.update_date, 'yyyy-mm-dd') update_date, "
					+ " (SELECT count(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) scnt, (SELECT avg(star) FROM star WHERE star.recipe = r.no GROUP BY recipe) savg, "
					+ " (SELECT count(no) FROM reply WHERE reply.recipe = r.no GROUP BY recipe) crep "
					+ " FROM recipe r, member m WHERE r.no = ? AND r.writer = m.id ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new RecipeVO();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setWriter(rs.getString(4));
				vo.setWrite_date(rs.getString(5));
				vo.setUpdate_date(rs.getString(6));
				vo.setCntStar(rs.getLong(7));
				vo.setAvrStar(rs.getDouble(8));
				vo.setCntReply(rs.getLong(9));
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
	public List<IngredientVO> viewTag(Long no) throws Exception {
		List<IngredientVO> list = null;
		try {
			con = DB.getConnection();
			String sql = "SELECT i.no, i.name FROM ingredient i, rec_ingr ri WHERE ri.rec_no = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				IngredientVO vo = new IngredientVO();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
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
	public List<ReplyVO> viewReply(Long no) throws Exception {
		List<ReplyVO> list = null;
		try {
			con = DB.getConnection();
			String sql = "SELECT r.content, m.nickname, to_char(r.write_date, 'yyyy-mm-dd hh:mi:ss') write_date "
					+ " FROM reply r, member m WHERE r.recipe = ? AND r.writer = m.id ORDER BY r.no";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				ReplyVO vo = new ReplyVO();
				vo.setContent(rs.getString(1));
				vo.setWriter(rs.getString(2));
				vo.setWrite_date(rs.getString(3));
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
	public Integer write(RecipeVO rec) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO recipe (no, title, content, writer) VALUES (recipe_seq.NEXTVAL, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rec.getTitle());
			pstmt.setString(2, rec.getContent());
			pstmt.setString(3, rec.getWriter());
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public boolean checkIng(String name) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT no FROM ingredient WHERE name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer writeIng(String name) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO ingredient (no, name) VALUES(ingredient_seq.nextval, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Long findRecNo() throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT max(no) FROM recipe";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getLong(1);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Long findIngNo(String name) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT no FROM ingredient WHERE name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			rs.next();

			return rs.getLong(1);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer writeI_R(Long in, Long rn) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO rec_ingr VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, in);
			pstmt.setLong(2, rn);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

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
