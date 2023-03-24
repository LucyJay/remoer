package com.remoer.member.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.member.vo.LoginVO;

public class MemberDAOImpl extends DAO implements MemberDAO {

	@Override
	public LoginVO login(LoginVO vo) throws Exception {
		try {
			LoginVO loginVO = null;
			con = DB.getConnection();
			String sql = "SELECT m.id, m.pw, m.name, m.nickname, m.birth, m.address, m.tel, m.email, m.reg_date, m.login_date, m.grade grade_no, g.grade_name, m.status "
					+ " FROM member m, grade g WHERE id = ? AND pw = ? AND m.grade = g.Grade_no";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			PreparedStatement pstmt2 = con
					.prepareStatement("UPDATE member SET login_date = sysdate WHERE id = ? AND pw = ?");
			pstmt2.setString(1, vo.getId());
			pstmt2.setString(2, vo.getPw());
			if (pstmt2.executeUpdate() == 1) {
				rs = pstmt.executeQuery();
				if (rs.next()) {
					loginVO = new LoginVO();
					loginVO.setId(rs.getString("id"));
					loginVO.setPw(rs.getString("pw"));
					loginVO.setName(rs.getString("name"));
					loginVO.setNickname(rs.getString("nickname"));
					loginVO.setBirth(rs.getString("birth"));
					loginVO.setAddress(rs.getString("address"));
					loginVO.setTel(rs.getString("tel"));
					loginVO.setEmail(rs.getString("email"));
					loginVO.setReg_date(rs.getString("reg_date"));
					loginVO.setLogin_date(rs.getString("login_date"));
					loginVO.setGrade_no(rs.getInt("grade_no"));
					loginVO.setGrade_name(rs.getString("grade_name"));
					loginVO.setStatus(rs.getString("status"));
				}
			}

			return loginVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("로그인 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public boolean checkId(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT id FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return false;

			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ID 중복 확인 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer join(LoginVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO member (id, pw, nickname, name, birth, address, tel, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getNickname());
			pstmt.setString(4, vo.getName());
			pstmt.setString(5, vo.getBirth());
			pstmt.setString(6, vo.getAddress());
			pstmt.setString(7, vo.getTel());
			pstmt.setString(8, vo.getEmail());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("회원가입 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public String findId(LoginVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT id FROM member WHERE name = ? AND tel = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getTel());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("id");
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ID 중복 확인 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer updatePw(LoginVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET pw = ? WHERE id = ? AND name = ? AND tel = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getTel());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("PW 변경 처리 중 오류 발생");
		} finally {
			close();
		}
	}

	@Override
	public Integer updateCondate(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET login_date = sysdate WHERE id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public boolean idExists(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "SELECT id FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public LoginVO view(String id) throws Exception {
		LoginVO vo = null;
		try {
			con = DB.getConnection();
			String sql = "SELECT m.id, m.nickname, m.name, to_char(m.birth, 'yyyy-mm-dd') birth, m.address, m.tel, m.email, to_char(m.reg_date, 'yyyy-mm-dd') reg_date, to_char(m.login_date, 'yyyy-mm-dd hh24:mi:ss') login_date, m.status, m.grade, g.grade_name FROM member m, grade g WHERE id = ? AND m.grade = g.grade_no ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new LoginVO();
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setName(rs.getString("name"));
				vo.setBirth(rs.getString("birth"));
				vo.setAddress(rs.getString("address"));
				vo.setTel(rs.getString("tel"));
				vo.setEmail(rs.getString("email"));
				vo.setReg_date(rs.getString("reg_date"));
				vo.setLogin_date(rs.getString("login_date"));
				vo.setStatus(rs.getString("status"));
				vo.setGrade_no(rs.getInt("grade"));
				vo.setGrade_name(rs.getString("grade_name"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public Integer updateInfo(LoginVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET address = ?, tel = ?, email = ? WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getAddress());
			pstmt.setString(2, vo.getTel());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public Integer updateStatus(LoginVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET status = ? WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getStatus());
			pstmt.setString(2, vo.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public Integer updateGrade(LoginVO vo) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET grade = ? WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getGrade_no());
			pstmt.setString(2, vo.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public Integer wakeUp(String id) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET status = '정상' WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public List<LoginVO> list(String type) throws Exception {
		List<LoginVO> list = null;
		try {
			con = DB.getConnection();
			String sql = "";
			if (type == null) {
				sql = "SELECT m.id, m.nickname, m.name, to_char(m.birth, 'yymmdd') birth, g.grade_name, m.status FROM member m, grade g WHERE m.grade = g.grade_no ORDER BY m.id";
				pstmt = con.prepareStatement(sql);
			} else {
				sql = "SELECT m.id, m.nickname, m.name, to_char(m.birth, 'yymmdd') birth, g.grade_name, m.status FROM member m, grade g WHERE m.status = ? AND m.grade = g.grade_no ORDER BY m.id";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, type);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<>();
				LoginVO vo = new LoginVO();
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
				vo.setName(rs.getString("name"));
				vo.setBirth(rs.getString("birth"));
				vo.setGrade_name(rs.getString("grade_name"));
				vo.setStatus(rs.getString("status"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}

	@Override
	public Integer rest(Date date) throws Exception {
		try {
			con = DB.getConnection();
			String sql = "UPDATE member SET status = '휴면' WHERE login_date < ? AND status = '정상'";
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, date);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			close();
		}
	}
}
