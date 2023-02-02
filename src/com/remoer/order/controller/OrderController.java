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
import com.remoer.order.service.OrderCancelServicelmpl;
import com.remoer.order.service.OrderListServiceImpl;
import com.remoer.order.service.OrderServiceImpl;
import com.remoer.order.service.OrderUpdateAddressServiceImpl;
import com.remoer.order.service.OrderUpdateDlvServiceImpl;
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
					Out.titleMini("배송지 수정");
					Out.sys("현재 배송준비중인 경우에만 배송지를 수정할 수 있습니다.");
					Long addressNo = In.getLong("확인할 주문번호");
					OrderVO addressVO = (OrderVO) Execute.run(new OrderViewServiceImpl(), addressNo);
					if (addressVO == null || !(addressVO.getId().equals(Main.login.getId()))) {
						Out.sysln("존재하지 않는 주문번호이거나 본인의 주문번호가 아닙니다.");
					} else if (!(addressVO.getStatus().equals("배송준비중"))) {
						Out.sysln("이미 배송이 시작되어 배송지를 수정할 수 없습니다.");
					} else {
						addressVO.setAddress(In.getStr("배송지"));
						addressVO.setName(In.getStr("수령인"));
						addressVO.setTel(In.getStr("연락처"));
						if ((Integer) Execute.run(new OrderUpdateAddressServiceImpl(), addressVO) == 1) {
							Out.sysln("배송지가 정상적으로 수정되었습니다.");
						} else
							Out.sysln("배송지가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
					}
					break;
				case "4":
					Long cancelNo = In.getLong("취소할 주문번호");
					OrderVO cancelVO = (OrderVO) Execute.run(new OrderViewServiceImpl(), cancelNo);
					if (cancelVO == null || !(cancelVO.getId().equals(Main.login.getId()))) {
						Out.sysln("존재하지 않는 주문번호이거나 본인의 주문번호가 아닙니다.");
					} else if (!(cancelVO.getStatus().equals("배송준비중"))) {
						Out.sysln("이미 배송이 시작되어 취소할 수 없습니다.");
					} else {
						if ((Integer) Execute.run(new OrderCancelServicelmpl(), cancelNo) == 1) {
							Out.sysln("배송지가 정상적으로 수정되었습니다.");
						} else
							Out.sysln("배송지가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
					}
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "5":
					if (Main.isAdmin()) {
						dlv: while (true) {
							Out.titleMini("배송상태 수정");
							Out.sys("1. 배송시작  2. 배송완료  0. 이전 메뉴");
							switch (In.getStr("")) {
							case "1":
								Out.sys("배송상태를 '배송준비중'에서 '배송중'으로 변경합니다.");
								Long dlv1No = In.getLong("배송중으로 변경할 주문번호");
								OrderVO dlv1VO = (OrderVO) Execute.run(new OrderViewServiceImpl(), dlv1No);
								if (dlv1VO == null) {
									Out.sysln("존재하지 않는 주문번호입니다.");
								} else if (!(dlv1VO.getStatus().equals("배송준비중"))) {
									Out.sysln("상태가 '배송준비중'인 주문번호가 아닙니다.");
								} else {
									dlv1VO.setStatus("배송중");
									if ((Integer) Execute.run(new OrderUpdateDlvServiceImpl(), dlv1VO) == 1) {
										Out.sysln("배송상태가 정상적으로 수정되었습니다.");
									} else
										Out.sysln("배송상태가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
								}
								break dlv;

							case "2":
								Out.sys("배송상태를 '배송중'에서 '배송완료'로 변경합니다.");
								Long dlv2No = In.getLong("배송완료로 변경할 주문번호");
								OrderVO dlv2VO = (OrderVO) Execute.run(new OrderViewServiceImpl(), dlv2No);
								if (dlv2VO == null) {
									Out.sysln("존재하지 않는 주문번호입니다.");
								} else if (!(dlv2VO.getStatus().equals("배송중"))) {
									Out.sysln("상태가 '배송중'인 주문번호가 아닙니다.");
								} else {
									dlv2VO.setStatus("배송완료");
									if ((Integer) Execute.run(new OrderUpdateDlvServiceImpl(), dlv2VO) == 1) {
										Out.sysln("배송상태가 정상적으로 수정되었습니다.");
									} else
										Out.sysln("배송상태가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
								}
								break dlv;
							case "0":
								Out.sysln("이전 메뉴로 돌아갑니다.");
								break dlv;
							}
						}
						break;
					}
				default:
					Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
				}

			} catch (

			Exception e) {
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
