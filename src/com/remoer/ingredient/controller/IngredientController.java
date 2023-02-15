package com.remoer.ingredient.controller;

import java.util.List;

import com.remoer.ingredient.io.PrintIngredient;
import com.remoer.ingredient.service.IngredientDeleteServiceImpl;
import com.remoer.ingredient.service.IngredientListServiceImpl;
import com.remoer.ingredient.service.IngredientUpdateServiceImpl;
import com.remoer.ingredient.service.IngredientViewServiceImpl;
import com.remoer.ingredient.service.IngredientWriteServiceImpl;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.Check;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.order.controller.OrderController;

public class IngredientController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { { "식재료 판매 리스트", "식재료 보기" }, {}, { "판매등록", "상품정보 수정", "판매중지" } };
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
					break;
				case "2":
					Long viewNo = In.getLong("확인할 상품번호");
					IngredientVO viewVO = (IngredientVO) Execute.run(new IngredientViewServiceImpl(), viewNo);
					PrintIngredient.print(viewVO);
					if (viewVO != null) {
						view: while (true) {
							Out.lineln("-", 30);
							String[][] viewMenus = { { "구매", "장바구니 담기" }, {}, {} };
							Out.menuNum("이전 메뉴", 5, viewMenus);
							Out.lineln("-", 30);
							switch (In.getStr()) {
							case "1":
								if (Main.login != null) {
									while (true) {
										int quantity = In.getInt("구매할 수량");
										if (quantity > viewVO.getQuantity()) {
											Out.sys("재고가 부족합니다. 다시 입력해 주세요.");
											continue;
										}
										viewVO.setQuantity(quantity);
										break;
									}
									OrderController oc = new OrderController();
									oc.order(oc.buy(viewVO));
								} else {
									Out.sysln("메인-회원관리 탭에서 로그인 후 이용할 수 있습니다..");
									break view;
								}
								break view;
							case "2":
								if (Main.login != null) {
									// 식재료 장바구니 담기

								} else {
									Out.sysln("메인-회원관리 탭에서 로그인 후 이용할 수 있습니다..");
									break view;
								}
							case "0":
								Out.sysln("이전 메뉴로 돌아갑니다.");
								break view;
							default:
								Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
							}
						}
					}
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "3":
					if (Main.isAdmin()) {
						Out.titleMini("식재료 상품 등록", 30);
						IngredientVO writeVO = new IngredientVO();
						while (true) {
							String iname = In.getStr("식재료명");
							if (!Check.isAllKor(iname)) {
								Out.sys("한글로만 입력 가능합니다.");
							} else {
								writeVO.setName(iname);
								break;
							}
						}
						writeVO.setDescription(In.getStr("설명"));
						writeVO.setPrice(In.getInt("가격(단위: 원)"));
						writeVO.setQuantity(In.getInt("재고"));
						Integer result = (Integer) Execute.run(new IngredientWriteServiceImpl(), writeVO);
						if (result == -1) {
							Out.sysln("이미 판매 중인 식재료입니다. 수정 메뉴를 이용해 주세요.");
						} else if (result == 1) {
							Out.sysln("식재료가 정상적으로 판매등록되었습니다.");
						} else
							Out.sysln("식재료가 정상적으로 판매등록되지 않았습니다. 확인해 주세요.");
						break;
					}
				case "4":
					if (Main.isAdmin()) {
						Out.titleMini("식재료 상품정보 수정", 30);
						Long updateNo = In.getLong("수정할 상품번호");
						IngredientVO updateVO = (IngredientVO) Execute.run(new IngredientViewServiceImpl(), updateNo);
						PrintIngredient.print(updateVO);
						if (updateVO != null) {
							update: while (true) {
								Out.sys("수정할 항목을 선택하세요.");
								Out.sys("1. 설명  2. 가격  3. 재고  9. 취소  0. 수정완료");
								switch (In.getStr("")) {
								case "1":
									updateVO.setDescription(In.getStr("설명"));
									break;
								case "2":
									updateVO.setPrice(In.getInt("가격"));
									break;
								case "3":
									updateVO.setQuantity(In.getInt("재고 추가량(감소는 음수로 입력)"));
									break;
								case "9":
									Out.sysln("수정을 취소하고 이전으로 돌아갑니다.");
									break update;
								case "0":
									if ((Integer) Execute.run(new IngredientUpdateServiceImpl(), updateVO) == 1) {
										Out.sysln("상품정보가 정상적으로 수정되었습니다.");
									} else
										Out.sysln("상품정보가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
									break update;
								default:
									Out.sys("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
								}
							}
						}
						break;
					}
				case "5":
					if (Main.isAdmin()) {
						Out.titleMini("식재료 판매중지", 30);
						Out.sys("판매중지 시 설명, 가격, 재고정보가 삭제되고 상품목록에서 사라집니다.");
						Long deleteNo = In.getLong("삭제할 상품번호");
						if ((Integer) Execute.run(new IngredientDeleteServiceImpl(), deleteNo) == 1) {
							Out.sysln("상품목록에서 정상적으로 삭제되었습니다.");
						} else
							Out.sysln("상품목록에서 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
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
