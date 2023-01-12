package com.remoer.recipe.io;

import java.util.List;

import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.recipe.vo.RecipeVO;
import com.remoer.recipe.vo.ReplyVO;

public class PrintRecipe {

	public static void print(List<RecipeVO> list) {
		System.out.println();
		System.out.println("\n================== 레시피나눔 ==================");
		System.out.println(" 번호(댓글수) | 제목 | 작성자 | 작성일 | 별점(참여 수)");
		System.out.println("----------------------------------------------");
		if (list == null || list.size() == 0) {
			System.out.println("* * * 작성된 레시피가 없습니다. * * *");
			System.out.println("* * 첫 번째 레시피를 작성해 보세요. * *");
		} else {
			for (int i = 0; i < list.size(); i++) {
				RecipeVO vo = list.get(i);
				System.out.println(vo.getNo() + "(" + vo.getCntReply() + ") | " + vo.getTitle() + " | " + vo.getWriter()
						+ " | " + vo.getWrite_date() + " | " + vo.getAvgStar() + "(" + vo.getCntStar() + ")");
			}
		}
		System.out.println("=============================================");
	}

	public static void print(RecipeVO vo) {
		if (vo == null) {
			System.out.println("\n================== 레시피나눔 ==================");
			System.out.println("* * * * * 존재하지 않는 글번호입니다. * * * * *");
			System.out.println("=============================================");
			return;
		}
		System.out.println("\n================== 레시피나눔 ==================");
		System.out.println(vo.getNo() + " | " + vo.getTitle() + "\t\t작성일 : " + vo.getWrite_date());
		System.out.println("----------------------------------------------");
		System.out.println(vo.getContent());
		System.out.print("재료 : ");
		if (vo.getIngreList() != null && vo.getIngreList().size() > 0) {
			for (IngredientVO ivo : vo.getIngreList()) {
				System.out.print("[" + ivo.getName() + "] ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("----------------------------------------------");
		System.out.println("Recipe by " + vo.getWriter());
		System.out.println("별점 " + vo.getAvgStar() + " (" + vo.getCntStar() + "명 참여)");
		System.out.println("----------------------------------------------");
		System.out.println("댓글 " + vo.getCntReply() + "개");
		System.out.println("----------------------------------------------");
		if (vo.getReplyList() != null && vo.getReplyList().size() > 0) {
			for (ReplyVO reply : vo.getReplyList()) {
				System.out.println(
						"[댓글"+reply.getNo()+"]" + reply.getWriter() + " | " + reply.getContent() + " - " + reply.getWrite_date());
			}
		}
		System.out.println("=============================================");
	}

}
