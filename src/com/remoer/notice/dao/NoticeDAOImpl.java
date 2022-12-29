package com.remoer.notice.dao;

import java.util.ArrayList;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.notice.vo.NoticeVO;

public class NoticeDAOImpl extends DAO implements NoticeDAO {

	@Override
	public List<NoticeVO> list(String period) throws Exception {
		try {
			List<NoticeVO> list = null;
			String per = "";
			switch (period) {
			case "1":
				per = " (start_date <= sysdate) AND (sysdate <= end_date + 1 ) ";
				break;
			case "2":
				per = " sysdate > end_date + 1 ";
				break;
			case "3":
				per = " start_date > sysdate ";
				break;
			}
			con = DB.getConnection();
			String sql = "SELECT no, title FROM notice WHERE " + per + " ORDER BY no DESC";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				NoticeVO vo = new NoticeVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("공지 리스트 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public NoticeVO view(Long no) throws Exception {
		try {
			NoticeVO vo = null;
			con = DB.getConnection();
			String sql = "SELECT no, title, content, to_char(start_date, 'yyyy-mm-dd') start_date, "
					+ " to_char(end_date, 'yyyy-mm-dd') end_date, to_char(update_date, 'yyyy-mm-dd') update_date "
					+ " FROM notice WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new NoticeVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setStart_date(rs.getString("start_date"));
				vo.setEnd_date(rs.getString("end_date"));
				vo.setUpdate_date(rs.getString("update_date"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("공지 보기 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer write(NoticeVO vo) throws Exception {
		try {
			con = DB.getConnection();
			if (vo.getStart_date() == null) {
				if (vo.getEnd_date() == null) {
					String sql = "INSERT INTO notice (no, title, content) VALUES (notice_seq.NEXTVAL, ?, ?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, vo.getTitle());
					pstmt.setString(2, vo.getContent());
				} else {
					String sql = "INSERT INTO notice (no, title, content, end_date) VALUES (notice_seq.NEXTVAL, ?, ?, ?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, vo.getTitle());
					pstmt.setString(2, vo.getContent());
					pstmt.setString(3, vo.getEnd_date());
				}
			} else if (vo.getEnd_date() == null) {
				String sql = "INSERT INTO notice (no, title, content, start_date) VALUES (notice_seq.NEXTVAL, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setString(3, vo.getStart_date());

			} else {
				String sql = "INSERT INTO notice (no, title, content, start_date, end_date) VALUES (notice_seq.NEXTVAL, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setString(3, vo.getStart_date());
				pstmt.setString(4, vo.getEnd_date());
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("공지 등록 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer update(NoticeVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE notice SET title = ?, content = ?, start_date = ?, end_date = ?, update_date = sysdate WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getStart_date());
			pstmt.setString(4, vo.getEnd_date());
			pstmt.setLong(5, vo.getNo());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("공지 수정 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer delete(Long no) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM notice WHERE no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("공지 삭제 중 오류 발생");
		} finally {
			close();
		}
	}
}
