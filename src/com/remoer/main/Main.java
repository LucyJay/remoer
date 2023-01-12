package com.remoer.main;

import com.remoer.member.controller.MemberController;
import com.remoer.member.vo.LoginVO;
import com.remoer.notice.controller.NoticeController;
import com.remoer.recipe.controller.RecipeController;

public class Main {

	public static LoginVO login = null;

	public static boolean isAdmin() {
		return (login != null) && (login.getGrade_no() == 9);
	};

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			Out.err("DB와의 연결이 원활하지 않아 프로그램을 종료합니다.");
			System.exit(1);
		}
		while (true) {
			String[][] menus = { { "공지사항/이벤트", "레시피나눔", "식재료", "회원정보", "고객센터" }, { "주문/배송", "장바구니", "북마크" }, {} };
			Out.menu("REMOER에 오신 것을 환영합니다", 3, "종료", menus);

			switch (In.getStr("메뉴를 입력하세요")) {
			case "1":
				new NoticeController().execute();
				break;
			case "2":
				new RecipeController().execute();
				break;
			case "3":
				break;
			case "4":
				new MemberController().execute();
				break;
			case "5":
				break;
			case "6":
				if (login != null) {
					break;
				}
			case "7":
				if (login != null) {
					break;
				}
			case "0":
				Out.sysln("프로그램을 종료합니다.");
				System.exit(0);
			default:
				Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

			}

		}
	}

}
