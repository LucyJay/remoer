package com.remoer.recipe.controller;

import java.util.ArrayList;
import java.util.List;

import com.remoer.main.Check;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.recipe.io.PrintRecipe;
import com.remoer.recipe.service.RecipeListServiceImpl;
import com.remoer.recipe.service.RecipeMyListServiceImpl;
import com.remoer.recipe.service.RecipeViewServiceImpl;
import com.remoer.recipe.service.RecipeWriteServiceImpl;
import com.remoer.recipe.vo.RecipeVO;

public class RecipeController {

	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { { "이 달의 Best", "레시피리스트", "레시피 보기" }, { "레시피 올리기", "내 레시피", "레시피 수정", "레시피 삭제" },
						{} };
				Out.menu("레시피나눔", 3, "이전 메뉴", menus);

				switch (In.inStr("메뉴를 입력하세요")) {

				case "1":
					PrintRecipe.print((List<RecipeVO>) Execute.run(new RecipeListServiceImpl(), true));
					break;
				case "2":
					PrintRecipe.print((List<RecipeVO>) Execute.run(new RecipeListServiceImpl(), false));
					break;
				case "3":
					Long viewNo = In.inLong("확인할 글번호");
					RecipeVO viewVO = (RecipeVO) Execute.run(new RecipeViewServiceImpl(), viewNo);
					PrintRecipe.print(viewVO);
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "4":
					if (Main.login != null) {
						Out.titleMini("레시피 등록", 30);
						RecipeVO writeVO = new RecipeVO();
						List<String> ingList = new ArrayList<>();
						writeVO.setTitle(In.inStr("제목"));
						writeVO.setContent(In.inStr("내용"));
						writeVO.setWriter(Main.login.getId());
						Out.sys("사용된 식재료를 모두 태그해 주세요. 모두 입력한 후 0을 입력해 주세요.");
						while (true) {
							String tag = In.inStr("");
							if (tag.equals("0")) {
								break;
							} else if (!Check.isAllKor(tag)) {
								Out.sys("한글로만 입력 가능합니다.");
							} else {
								ingList.add(tag);
							}
						}
						if ((Integer) Execute.run(new RecipeWriteServiceImpl(),
								new Object[] { writeVO, ingList }) == 1) {
							Out.sysln("레시피가 정상적으로 등록되었습니다.");
						} else
							Out.sysln("레시피가 정상적으로 등록되지 않았습니다. 다시 시도해 주세요.");
						break;
					}
				case "5":
					if (Main.login != null) {
						
						PrintRecipe.print((List<RecipeVO>) Execute.run(new RecipeMyListServiceImpl(), Main.login.getId()));
						
						break;
					}
				case "6":
					if (Main.login != null) {
						Out.titleMini("레시피 수정", 30);
						
						break;
					}
				case "7":
					if (Main.login != null) {
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
