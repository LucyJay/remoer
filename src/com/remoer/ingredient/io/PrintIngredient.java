package com.remoer.ingredient.io;

import java.util.List;

import com.remoer.ingredient.vo.IngredientVO;

public class PrintIngredient {

	public static void print(List<IngredientVO> list) {
		System.out.println();
		System.out.println("\n====== 식재료마켓 =======");
		System.out.println(" 번호 |   재료명   |  가격");
		System.out.println("-----------------------");
		if (list == null || list.size() == 0) {
			System.out.println("* 현재 판매 중인 식재료가 없습니다. *");
		} else {
			for (int i = 0; i < list.size(); i++) {
				IngredientVO vo = list.get(i);
				System.out.println(vo.getNo() + " | " + (vo.getQuantity() == 0 ? "[품절]" : "") + vo.getName() + " | "
						+ vo.getPrice());
			}
		}
		System.out.println("=======================");
	}

	public static void print(IngredientVO vo) {
		if (vo == null) {
			System.out.println("\n================== 식재료마켓 ==================");
			System.out.println("* * * * * 판매 중인 식재료의 번호가 아닙니다. * * * * *");
			System.out.println("=============================================");
			return;
		}
		System.out.println("\n================== 식재료마켓 ==================");
		System.out.println(vo.getNo() + " | " + vo.getName());
		System.out.println("----------------------------------------------");
		System.out.println("설명: " + vo.getDescription());
		System.out.print("가격: " + vo.getPrice() + "원");
		System.out.print("재고: " + vo.getQuantity() + "개");
		System.out.println("=============================================");
	}

}
