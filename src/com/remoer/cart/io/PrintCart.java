package com.remoer.cart.io;

import java.util.List;

import com.remoer.cart.vo.CartVO;

public class PrintCart {
	public static void print(List<CartVO> list) {
		System.out.println();
		System.out.println("\n============= 장바구니 =============");
		System.out.println(" 번호 |   상품명   | 가격 | 수량 | 총금액");
		System.out.println("----------------------------------");
		if (list == null || list.size() == 0) {
			System.out.println("* 장바구니에 담은 상품이 없습니다. *");
		} else {
			int total_price = 0;
			for (int i = 0; i < list.size(); i++) {
				CartVO vo = list.get(i);
				System.out.println(vo.getNo() + " | " + vo.getGoods_name() + " | " + vo.getPrice() + " | "
						+ vo.getQuantity() + " | " + vo.totalPrice());
				total_price += vo.totalPrice();
			}
			System.out.println("----------------------------------");
			System.out.println("총 가격:" + total_price + "원");
		}
		System.out.println("==================================");

	}
}
