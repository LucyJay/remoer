package com.remoer.member.dao;

import java.sql.PreparedStatement;

import com.remoer.dao.DAO;
import com.remoer.dao.DB;
import com.remoer.member.vo.LoginVO;

public class MemberDAOImpl extends DAO implements MemberDAO {

	@Override
	public LoginVO login(LoginVO vo) throws Exception {
		try {
			LoginVO loginVO = null;
			con = DB.getConnection();
			String sql = "SELECT m.id, m.pw, m.name, m.birth, m.address, m.tel, m.email, m.reg_date, m.login_date, m.grade grade_no, g.grade_name "
					+ " FROM member m, grade g WHERE id = ? AND pw = ? AND m.grade = g.grade_no";
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
					loginVO.setBirth(rs.getString("birth"));
					loginVO.setAddress(rs.getString("address"));
					loginVO.setTel(rs.getString("tel"));
					loginVO.setEmail(rs.getString("email"));
					loginVO.setReg_date(rs.getString("reg_date"));
					loginVO.setLogin_date(rs.getString("login_date"));
					loginVO.setGrade_no(rs.getInt("grade_no"));
					loginVO.setGrade_name(rs.getString("grade_name"));
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
			String sql = "INSERT INTO member (id, pw, name, birth, address, tel, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getBirth());
			pstmt.setString(5, vo.getAddress());
			pstmt.setString(6, vo.getTel());
			pstmt.setString(7, vo.getEmail());

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
			throw new Exception("ID 중복 확인 처리 중 오류 발생");
		} finally {
			close();
		}
	}

}
