package com.remoer.member.recipe.controller;

import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;


public class RecipeController {

	public void execute() {
		while (true) {
			try {

				if (Main.login == null) {
					String[][] menus = { { "이 달의 Best", "레시피리스트", "레시피 보기" },
							{ "레시피 올리기", "내 레시피", "레시피 수정", "레시피 삭제" }, {} };
					Out.menu("레시피나눔", 4, "이전 메뉴", menus);

					switch (In.inStr("메뉴를 입력하세요 ▶ ")) {

					case "1":

						break;
					case "2":

						break;
					case "3":

						break;
					case "0":
						Out.sysln("이전 메뉴로 돌아갑니다.");
						return;
					case "4":
						if (Main.login != null) {
							break;
						}
					case "5":
						if (Main.login != null) {
							break;
						}
					case "6":
						if (Main.login != null) {
							break;
						}
					case "7":
						if (Main.login != null) {
							break;
						}
					default:
						Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}
	}

}
