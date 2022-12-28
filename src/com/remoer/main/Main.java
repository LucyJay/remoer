package com.remoer.main;

import com.remoer.member.controller.MemberController;
import com.remoer.member.vo.LoginVO;
import com.remoer.notice.controller.NoticeController;

public class Main {

	public static LoginVO login = null;

	public static boolean isAdmin() {
		return (login != null) && (login.getGrade_no() == 9);
	};

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("연결이 원활하지 않아 프로그램을 종료합니다.");
			System.exit(1);
		}
		while (true) {
			if (login == null) {
				System.out.println("\n\n========= 레시피로 모인 사람들 REMOER ===========");
				System.out.println("-------- REMOER에 오신 것을 환영합니다. ----------");
				System.out.println("1. 공지사항/이벤트  2. 레시피나눔  3. 로그인/회원가입");
				System.out.println("4. 고객센터  0. 종료");
				System.out.println("--------------------------------------------");
				String menu = In.inStr("메뉴를 입력하세요 ▶ ");

				switch (menu) {
				case "1":
					new NoticeController().execute();
					break;
				case "2":
					break;
				case "3":
					new MemberController().execute();
					break;
				case "4":
					break;
				case "0":
					System.out.println("[프로그램을 종료합니다.]");
					System.exit(0);
				default:
					System.out.println("[잘못 누르셨습니다. 메뉴번호를 확인해 주세요.]");

				}
			} else {
				System.out.println("\n========= 레시피로 모인 사람들 REMOER ===========");
				System.out.println("\n--- " + login.getName() + "님 Remoer에 오신 것을 환영합니다.---");
				System.out.println("1. 공지사항/이벤트  2. 레시피나눔  3. 식재료구매  4. 장바구니");
				System.out.println("5. 주문/배송  6. 회원정보  7. 고객센터  0. 종료");
				System.out.println("--------------------------------------------------");
				String menu = In.inStr("메뉴를 입력하세요 ▶ ");

				switch (menu) {
				case "1":
					new NoticeController().execute();
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				case "5":
					break;
				case "6":
					new MemberController().execute();
					break;
				case "7":
					break;
				case "0":
					System.out.println("[프로그램을 종료합니다.]");
					System.exit(0);
				default:
					System.out.println("[잘못 누르셨습니다. 메뉴번호를 확인해 주세요.]");

				}
			}
		}
	}

}
