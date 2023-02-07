package com.remoer.member.io;

import java.util.List;

import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.member.vo.LoginVO;

public class PrintMember {
	public static void print(List<LoginVO> list) {
		if (list == null || list.size() == 0) {
			System.out.println();
			Out.lineln("-", 50);
			System.out.println(" * * * 검색 조건에 해당하는 회원이 존재하지 않습니다. * * *");
			Out.lineln("-", 50);
		} else {
			System.out.println();
			System.out.println();
			Out.title("회원 리스트");
			System.out.println(" 아이디 | 닉네임 | 이름 | 생년월일 | 성별 | 회원등급 | 상태");
			Out.lineln("-", 45);
			for (int i = 0; i < list.size(); i++) {
				LoginVO vo = list.get(i);
				System.out.println(vo.getId() + " | " + vo.getNickname() + " | " + vo.getName() + " | " + vo.getBirth()
						+ " | " + vo.getGender() + " | " + vo.getGrade_name() + " | " + vo.getStatus());
			}
			Out.lineln("=", 45);
		}
	}

	public static void print(LoginVO vo) {
		if (vo == null) {
			System.out.println();
			Out.lineln("-", 40);
			System.out.println(" * * * 해당하는 회원이 존재하지 않습니다. * * *");
			Out.lineln("-", 40);
			System.out.println();
		} else {
			System.out.println();
			Out.titleMini(vo.getName() + "(" + vo.getId() + ")님의 회원정보", 32);
			System.out.println("아이디 : " + vo.getId());
			System.out.println("닉네임 : " + vo.getNickname());
			System.out.println("이름 : " + vo.getName());
			System.out.println("주소 : " + vo.getAddress() + "(기본배송지)");
			System.out.println("전화번호 : " + vo.getTel());
			System.out.println("이메일 : " + vo.getEmail());
			if (Main.isAdmin()) {
				System.out.println("생년월일 : " + vo.getBirth());
				System.out.println("성별 : " + vo.getGender());
				System.out.println("회원등급 : " + vo.getGrade_name());
				System.out.println("상태 : " + vo.getStatus());
				System.out.println("가입일 : " + vo.getReg_date());
				System.out.println("최종방문일시 : " + ((vo.getLogin_date() == null) ? "로그인 이력 없음" : vo.getLogin_date()));
			}
			Out.lineln("-", 40);
		}
	}
}
