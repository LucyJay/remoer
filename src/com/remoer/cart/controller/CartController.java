package com.remoer.cart.controller;

import java.util.ArrayList;
import java.util.List;

import com.remoer.cart.io.PrintCart;
import com.remoer.cart.service.CartDeleteAllServiceImpl;
import com.remoer.cart.service.CartDeleteServiceImpl;
import com.remoer.cart.service.CartListServiceImpl;
import com.remoer.cart.service.CartUpdateQuantityServiceImpl;
import com.remoer.cart.service.CartViewServiceImpl;
import com.remoer.cart.service.CartWriteServiceImpl;
import com.remoer.cart.vo.CartVO;
import com.remoer.ingredient.vo.GoodsVO;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.order.controller.OrderController;

public class CartController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { {}, { "목록", "일부주문", "전체주문", "수량변경", "일부삭제", "비우기" }, {} };
				Out.menu("주문/배송조회", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					PrintCart.print((List<CartVO>) Execute.run(new CartListServiceImpl(), Main.login.getId()));
					break;
				case "2":
					Out.sys("구매할 장바구니번호를 모두 선택한 후 0번을 눌러 주세요.");
					List<GoodsVO> orderList = new ArrayList<>();
					List<Long> orderNoList = new ArrayList<>();
					order: while (true) {
						Long orderNo = In.getLong("");
						if (orderNo == 0L) {
							break order;
						} else {
							orderNoList.add(orderNo);
							CartVO orderVO = (CartVO) Execute.run(new CartViewServiceImpl(), orderNo);
							if (orderVO == null) {
								Out.sys("존재하지 않는 장바구니번호입니다.");
							} else if (!orderVO.getId().equals(Main.login.getId())) {
								Out.sys("본인의 장바구니번호가 아닙니다.");
							} else {
								orderList = OrderController.buy(orderList, orderVO);
							}
						}
					}
					if (orderList.size() < 1) {
						Out.sysln("선택하신 상품이 없어 이전으로 돌아갑니다.");
					} else {
						if (new OrderController().order(orderList)) {
							for (Long no : orderNoList) {
								Execute.run(new CartDeleteServiceImpl(), no);
							}
						}
					}
					break;
				case "3":
					Out.sys("장바구니에 있는 모든 상품을 주문하시겠습니다? (y/n)");
					buyAll: while (true) {
						switch (In.getStr("")) {
						case "y":
							List<CartVO> buyAllList = (List<CartVO>) Execute.run(new CartListServiceImpl(),
									Main.login.getId());
							List<GoodsVO> buyAllGoodsList = new ArrayList<>();
							if (buyAllList == null || buyAllList.size() < 1) {
								Out.sysln("장바구니에 담겨 있는 상품이 없습니다.");
							} else {
								for (CartVO allVO : buyAllList) {
									OrderController.buy(buyAllGoodsList, allVO);
								}
								if (new OrderController().order(buyAllGoodsList))
									Execute.run(new CartDeleteAllServiceImpl(), Main.login.getId());
							}
							break buyAll;
						case "n":
							break buyAll;
						default:
							Out.sysln("잘못 누르셨습니다. y 또는 n만 입력 가능합니다.");
						}
					}
					break;
				case "4":
					Long updateNo = In.getLong("수량을 변경할 장바구니번호");
					CartVO updateVO = (CartVO) Execute.run(new CartViewServiceImpl(), updateNo);
					if (updateVO == null || !(updateVO.getId().equals(Main.login.getId()))) {
						Out.sysln("존재하지 않는 장바구니번호이거나 본인의 장바구니번호가 아닙니다.");
					} else {
						updateVO.setQuantity(In.getInt("새로운 수량을 입력하세요"));
						if ((Integer) Execute.run(new CartUpdateQuantityServiceImpl(), updateVO) == 1) {
							Out.sysln("수량이 정상적으로 수정되었습니다.");
						} else
							Out.sysln("수량이 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
					}
					break;
				case "5":
					Long deleteNo = In.getLong("삭제할 장바구니번호");

					CartVO deleteVO = (CartVO) Execute.run(new CartViewServiceImpl(), deleteNo);
					if (deleteVO == null || !(deleteVO.getId().equals(Main.login.getId()))) {
						Out.sysln("존재하지 않는 장바구니번호이거나 본인의 장바구니번호가 아닙니다.");
					} else {
						if ((Integer) Execute.run(new CartDeleteServiceImpl(), deleteNo) == 1) {
							Out.sysln("선택하신 장바구니가 정상적으로 삭제되었습니다.");
						} else
							Out.sysln("선택하신 장바구니가 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
					}
					break;
				case "6":
					Out.sys("장바구니에 있는 모든 상품을 삭제하시겠습니다? (y/n)");
					deleteAll: while (true) {
						switch (In.getStr("")) {
						case "y":
							if ((Integer) Execute.run(new CartDeleteAllServiceImpl(), Main.login.getId()) == 1) {
								Out.sysln("장바구니를 정상적으로 비웠습니다.");
							} else
								Out.sysln("장바구니가 정상적으로 비워지지 않았습니다. 다시 시도해 주세요.");
							break deleteAll;
						case "n":
							break deleteAll;
						default:
							Out.sysln("잘못 누르셨습니다. y 또는 n만 입력 가능합니다.");
						}
					}
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				default:
					Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}
	}

	public boolean cart(IngredientVO ivo) {
		try {
			CartVO cvo = new CartVO();
			cvo.setGoods_no(ivo.getNo());
			cvo.setId(Main.login.getId());
			cvo.setQuantity(ivo.getQuantity());
			cvo.setPrice(ivo.getPrice());
			if ((boolean) Execute.run(new CartWriteServiceImpl(), cvo)) {
				Out.sysln("성공적으로 장바구니에 담았습니다.");
			} else {
				Out.sysln("이미 장바구니에 있는 상품입니다.");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			return false;
		}
	}
}
