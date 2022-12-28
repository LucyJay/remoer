package com.remoer.member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.remoer.member.vo.LoginVO;

public class MemberDAOImpl implements MemberDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final String ID = "remoer";
	final String PW = "remoer";

	@Override
	public LoginVO login(LoginVO vo) throws Exception {
		try {
			LoginVO loginVO = null;
			con = DriverManager.getConnection(URL, ID, PW);
			String sql = "SELECT m.id, m.pw, m.name, m.tel, m.grade grade_no, g.grade_name "
					+ " FROM member m, grade g WHERE id = ? AND pw = ? AND m.grade = g.grade_no";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loginVO = new LoginVO();
				loginVO.setId(rs.getString("id"));
				loginVO.setPw(rs.getString("pw"));
				loginVO.setName(rs.getString("name"));
				loginVO.setTel(rs.getString("tel"));
				loginVO.setGrade_no(rs.getInt("grade_no"));
				loginVO.setGrade_name(rs.getString("id"));
			}
			return loginVO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("로그인 처리 중 오류 발생");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("로그인 처리 중 오류 발생");
			}
		}
	}

}
