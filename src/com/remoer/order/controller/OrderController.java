package com.remoer.order.controller;

import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;

public class OrderController {
	public void execute() {
		while (true) {
			try {

				String[][] menus = { {}, { "구매내역", "구매내역상세", "배송지 수정", "주문취소" }, { "배송상태 변경" } };
				Out.menu("주문/배송조회", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "5":
					if (Main.isAdmin()) {
						break;
					}
				default:
					Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}
	}
}
