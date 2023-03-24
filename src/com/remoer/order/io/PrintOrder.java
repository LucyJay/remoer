package com.remoer.order.io;

import java.util.List;

import com.remoer.ingredient.vo.GoodsVO;
import com.remoer.main.Main;
import com.remoer.order.vo.OrderVO;

public class PrintOrder {

	public static void print(List<OrderVO> list) {
		System.out.println();
		System.out.println("\n========================= 구매내역 =========================");
		System.out.println(" 번호 |" + (Main.isAdmin() ? " 아이디 |" : "") + "  총결제금액  |  주문일시  |  배송상태  | 배송일자");
		System.out.println("----------------------------------------------------------");
		if (list == null || list.size() == 0) {
			System.out.println("* 구매내역이 없습니다. *");
		} else {
			for (int i = 0; i < list.size(); i++) {
				OrderVO vo = list.get(i);
				if (vo.getDlv_date() == null)
					vo.setDlv_date("-");
				System.out
						.println(vo.getNo() + " | " + (Main.isAdmin() ? (vo.getId() + " | ") : "") + vo.getTotalPrice()
								+ " | " + vo.getOrder_date() + " | " + vo.getStatus() + " | " + vo.getDlv_date());
			}
		}
		System.out.println("=========================================================");

	}

	public static void print(OrderVO vo) {
		if (vo == null) {
			System.out.println("\n================== 상세내역 ==================");
			System.out.println("* * * * * 본인의 유효한 구매번호가 아닙니다. * * * * *");
			System.out.println("=============================================");
			return;
		}
		if (vo.getDlv_date() == null)
			vo.setDlv_date("-");
		System.out.println("\n================== 상세내역 ==================");
		System.out.println("구매번호 " + vo.getNo());
		System.out.println("----------------------------------------------");
		System.out.println("받는사람: " + vo.getName());
		System.out.println("배송지: " + vo.getAddress());
		System.out.println("연락처: " + vo.getTel());
		System.out.println("주문일자: " + vo.getOrder_date());
		System.out.println("배송상태: " + vo.getStatus());
		System.out.println("배송완료일: " + vo.getDlv_date());
		for (GoodsVO gvo : vo.getList()) {
			System.out.println("----------------------------------------------");
			System.out.println("상품명: " + gvo.getGoods_name());
			System.out.println("가격: " + gvo.getPrice());
			System.out.println("구매수량: " + gvo.getQuantity());
			System.out.println("총금액: " + gvo.totalPrice());
		}
		System.out.println("----------------------------------------------");
		System.out.println("총결제금액: " + vo.totalPrice());
		System.out.println("=============================================");
	}

}
