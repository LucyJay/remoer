package com.remoer.order.controller;

import java.util.ArrayList;
import java.util.List;

import com.remoer.cart.vo.CartVO;
import com.remoer.ingredient.vo.GoodsVO;
import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.order.io.PrintOrder;
import com.remoer.order.service.OrderListServiceImpl;
import com.remoer.order.service.OrderServiceImpl;
import com.remoer.order.service.OrderViewServiceImpl;
import com.remoer.order.vo.OrderVO;

public class OrderController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { {}, { "구매내역", "구매내역상세", "배송지 수정", "주문취소" }, { "배송상태 변경" } };
				Out.menu("주문/배송조회", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					PrintOrder.print((List<OrderVO>) Execute.run(new OrderListServiceImpl(), Main.login.getId()));
					break;
				case "2":
					Long viewNo = In.getLong("확인할 주문번호");
					OrderVO viewVO = (OrderVO) Execute.run(new OrderViewServiceImpl(), viewNo);
					if (viewVO == null || !(viewVO.getId().equals(Main.login.getId()))) {
						Out.sysln("존재하지 않는 주문번호이거나 본인의 주문번호가 아닙니다.");
					} else {
						PrintOrder.print(viewVO);
					}
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

	public void order(List<GoodsVO> goods) {
		try {
			OrderVO vo = new OrderVO();
			vo.setId(Main.login.getId());
			Out.titleMini("배송정보 입력");
			Out.sys("1. 내 정보로 구매  2. 직접 입력하기  0. 취소");
			form: while (true) {
				switch (In.getStr("")) {
				case "1":
					vo.setName(Main.login.getName());
					vo.setAddress(Main.login.getAddress());
					vo.setTel(Main.login.getTel());
					break form;
				case "2":
					vo.setName(In.getStr("받는사람"));
					vo.setAddress(In.getStr("배송지"));
					vo.setTel(In.getStr("연락처"));
					break form;

				case "0":
					Out.sysln("구매를 취소합니다.");
					return;
				default:
					Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
				}
			}
			vo.setList(goods);
			if ((Integer) Execute.run(new OrderServiceImpl(), vo) == 1) {
				Out.sysln("상품정보가 정상적으로 수정되었습니다.");
			} else
				Out.sysln("상품정보가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Out.err("주문 중 오류가 발생했습니다. 다시 시도해 주세요.");
		}
	}

	public List<GoodsVO> buy(IngredientVO ivo) {
		List<GoodsVO> list = new ArrayList<>();
		GoodsVO vo = new GoodsVO();
		vo.setGoods_no(ivo.getNo());
		vo.setPrice(ivo.getPrice());
		vo.setQuantity(ivo.getQuantity());
		list.add(vo);
		return list;
	}

	public List<GoodsVO> buy(CartVO cvo) {
		List<GoodsVO> list = new ArrayList<>();
		GoodsVO vo = new GoodsVO();
		vo.setGoods_no(cvo.getNo());
		vo.setPrice(cvo.getPrice());
		vo.setQuantity(cvo.getQuantity());
		list.add(vo);
		return list;
	}
}
