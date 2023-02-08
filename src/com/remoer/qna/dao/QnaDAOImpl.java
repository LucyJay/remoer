package com.remoer.qna.dao;

import java.util.ArrayList;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.qna.vo.FaqVO;
import com.remoer.qna.vo.QnaVO;

public class QnaDAOImpl extends DAO implements QnaDAO {

	@Override
	public List<FaqVO> listFaq() throws Exception {
		try {
			List<FaqVO> list = null;
			con = DB.getConnection();
			String sql = "SELECT no, title, content, answer FROM faq ORDER BY no";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				FaqVO vo = new FaqVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setAnswer(rs.getString("answer"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("FAQ 리스트 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public List<QnaVO> listQna(String id) throws Exception {
		try {
			List<QnaVO> list = null;
			con = DB.getConnection();
			String sql = "";
			if (id == null) {
				sql = "SELECT no, title, writer, to_char(write_date, 'yyyy-mm-dd') write_date, answer_date FROM qna order by no";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "SELECT no, title, writer, to_char(write_date, 'yyyy-mm-dd') write_date, answer_date FROM qna WHERE writer = ? order by no";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				QnaVO vo = new QnaVO();
				vo.setNo(rs.getLong("no"));
				vo.setQtitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setQwriteDate(rs.getString("write_date"));
				if (rs.getString("answer_date") != null) {
					vo.setAnswer(true);
				}
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("QnA 리스트 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public QnaVO view(Long no) throws Exception {
		try {
			QnaVO vo = null;
			con = DB.getConnection();
			String sql = "SELECT no, title, content, writer, to_char(write_date, 'yyyy-mm-dd hh:mi:ss') write_date, answer_title, answer_content, to_char(answer_date, 'yyyy-mm-dd hh:mi:ss') answer_date FROM qna WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new QnaVO();
				vo.setNo(rs.getLong("no"));
				vo.setQtitle(rs.getString("title"));
				vo.setQcontent(rs.getString("content"));
				vo.setWriter(rs.getString("writer"));
				vo.setQwriteDate(rs.getString("write_date"));
				if (rs.getString("answer_date") != null) {
					vo.setAnswer(true);
					vo.setAtitle(rs.getString("answer_title"));
					vo.setAcontent(rs.getString("answer_content"));
					vo.setAwriteDate(rs.getString("answer_date"));
				}
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("QnA 보기 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer writeQ(QnaVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO qna (no, title, content, writer, write_date) VALUES (qna_seq.NEXTVAL, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getQtitle());
			pstmt.setString(2, vo.getQcontent());
			pstmt.setString(3, vo.getWriter());
			pstmt.setString(4, vo.getQwriteDate());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("문의 등록 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer updateQ(QnaVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE qna SET title = ?, content = ? WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getQtitle());
			pstmt.setString(2, vo.getQcontent());
			pstmt.setLong(3, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("문의 수정 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer deleteQ(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM qna WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("문의 삭제 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer writeA(QnaVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE qna SET answer_title = ?, answer_content = ?, answer_date = sysdate WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getAtitle());
			pstmt.setString(2, vo.getAcontent());
			pstmt.setLong(3, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("답변 등록 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer updateA(QnaVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE qna SET answer_title = ?, answer_content = ? WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getAtitle());
			pstmt.setString(2, vo.getAcontent());
			pstmt.setLong(3, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("공지 수정 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer deleteA(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE qna SET answer_title = null, answer_content = null, answer_date = null WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("QNA 삭제 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public FaqVO viewFaq(Long no) throws Exception {
		try {
			FaqVO vo = null;
			con = DB.getConnection();
			String sql = "SELECT no, title, content, answer FROM faq WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new FaqVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setAnswer(rs.getString("answer"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("FAQ 보기 처리 중 오류 발생");
		} finally {
			close();
		}
	};

	@Override
	public Integer writeFaq(FaqVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO faq (faq_seq.NEXTVAL, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getAnswer());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("FAQ 등록 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer updateFaq(FaqVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE qna SET title = ?, content = ?, answer = ? WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getAnswer());
			pstmt.setLong(4, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("FAQ 수정 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer deleteFaq(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM faq WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("FAQ 삭제 중 오류 발생");
		} finally {
			close();
		}
	}

}