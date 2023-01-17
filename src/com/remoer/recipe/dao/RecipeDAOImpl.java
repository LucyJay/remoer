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
				vo.setAvgStar(rs.getDouble(6));
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
				vo.setAvgStar(rs.getDouble(6));
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
			//recipeVO 일반 데이터
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
				vo.setAvgStar(rs.getDouble(8));
				vo.setCntReply(rs.getLong(9));
			}
			//식재료 태그 관련 데이터
			sql = "SELECT i.no, i.name FROM ingredient i, rec_ingr ri WHERE ri.rec_no = ? AND ri.ingr_no = i.no ";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (vo.getIngreList() == null)
					vo.setIngreList(new ArrayList<>());
				IngredientVO ingVO = new IngredientVO();
				ingVO.setNo(rs.getLong(1));
				ingVO.setName(rs.getString(2));
				vo.getIngreList().add(ingVO);
			}
			
			//댓글 데이터
			sql = "SELECT r.content, m.nickname, to_char(r.write_date, 'yyyy-mm-dd hh:mi:ss') write_date "
					+ " FROM reply r, member m WHERE r.recipe = ? AND r.writer = m.id ORDER BY r.no";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (vo.getReplyList() == null)
					vo.setReplyList(new ArrayList<>());
				ReplyVO repVO = new ReplyVO();
				repVO.setContent(rs.getString(1));
				repVO.setWriter(rs.getString(2));
				repVO.setWrite_date(rs.getString(3));
				vo.getReplyList().add(repVO);
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

	@Override
	public Integer star(StarVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO star VALUES(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getStar());
			pstmt.setString(2, vo.getId());
			pstmt.setLong(3, vo.getRecipe());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			return 0;
		} finally {
			close();
		}
	}

	@Override
	public Integer deStar(StarVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM star WHERE id = ? AND recipe = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setLong(2, vo.getRecipe());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer reply(ReplyVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO reply (no, recipe, content, writer) VALUES (reply_seq.nextval, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, vo.getRecipeNo());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWriter());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public boolean checkWriter(ReplyVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT no FROM reply WHERE writer = ? AND no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getWriter());
			pstmt.setLong(2, vo.getNo());
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
	public Integer updateReply(ReplyVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE reply SET content = ? WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getContent());
			pstmt.setLong(2, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer deleteReply(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM reply WHERE no = ?";
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
	public boolean checkWriter(RecipeVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT no FROM recipe WHERE writer = ? AND no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setLong(2, vo.getNo());
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
	public Integer deleteI_R(Long rn) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM rec_ingr WHERE rec_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, rn);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
		}
	}

	@Override
	public Integer update(RecipeVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE recipe SET title = ?, content = ?, update_date = sysdate WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
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
			String sql = "DELETE FROM recipe WHERE no = ?";
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
