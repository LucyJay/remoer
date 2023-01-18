package com.remoer.ingredient.controller;

import java.util.List;

import com.remoer.ingredient.io.PrintIngredient;
import com.remoer.ingredient.service.IngredientListServiceImpl;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;

public class IngredientController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { { "식재료 판매 리스트", "식재료 보기" }, {}, { "식재료 등록", "식재료 수정", "식재료 삭제" } };
				Out.menu("식재료마켓", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					while (true) {
						Out.sys("품절 상품을 포함할까요? (y/n)");
						String soldout = In.getStr("");
						if (soldout.equals("y")) {
							PrintIngredient
									.print((List<IngredientVO>) Execute.run(new IngredientListServiceImpl(), true));
							break;
						} else if (soldout.equals("n")) {
							PrintIngredient
									.print((List<IngredientVO>) Execute.run(new IngredientListServiceImpl(), false));
							break;
						} else
							Out.sys("'y' 또는 'n'만 입력할 수 있습니다.");
					}
				case "2":
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "3":
					if (Main.isAdmin()) {
						break;
					}
				case "4":
					if (Main.isAdmin()) {
						break;
					}
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
