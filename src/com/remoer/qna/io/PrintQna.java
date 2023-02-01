package com.remoer.qna.io;

import java.util.List;

import com.remoer.qna.vo.FaqVO;
import com.remoer.qna.vo.QnaVO;

public class PrintQna {

	public static void printFaq(List<FaqVO> list) {
		System.out.println("\n========  FAQ ==========");
		if (list == null || list.size() == 0)
			System.out.println("* * * 자주 묻는 사항이 없습니다. * * *");
		else {
			for (int i = 0; i < list.size(); i++) {
				if (i != 0)
					System.out.println("-----------------------------------");
				FaqVO vo = list.get(i);
				System.out.println(vo.getNo() + " | [질문]" + vo.getTitle());
				System.out.println(vo.getContent());
				System.out.println(">> 답변 : " + vo.getAnswer());
			}
		}
		System.out.println("================================");
	}

	public static void printFaq(FaqVO vo) {
		System.out.println("-----------------------------------");
		System.out.println(vo.getNo() + " | [질문]" + vo.getTitle());
		System.out.println(vo.getContent());
		System.out.println(">> 답변 : " + vo.getAnswer());
		System.out.println("-----------------------------------");
	}

	public static void print(List<QnaVO> list) {
		System.out.println("\n==================== 고객센터 ====================");
		System.out.println(" 번호 |            제목             | 작성자 | 작성일");
		System.out.println("--------------------------------");
		if (list == null || list.size() == 0)
			System.out.println("* * * 작성된 문의글이 없습니다. * * *");
		else {
			for (int i = 0; i < list.size(); i++) {
				QnaVO vo = list.get(i);
				System.out.println(vo.getNo() + " | " + vo.getQtitle() + (vo.isAnswer() ? "[답변완료]" : "[답변대기]") + " | "
						+ vo.getWriter() + " | " + vo.getQwriteDate());
			}
		}
		System.out.println("================================================");
	}

	public static void print(QnaVO vo) {
		if (vo == null) {
			System.out.println("=====================================");
			System.out.println("** 해당 번호의 문의글이 존재하지 않습니다. **");
		} else {
			System.out.println("[문의]" + vo.getQtitle() + "");
			System.out.println(vo.getQcontent());
			System.out.println("□작성자:" + vo.getWriter());
			System.out.println("□작성일시:" + vo.getQwriteDate());
			if (vo.isAnswer()) {
				System.out.println("-------------------------------------");
				System.out.println("[답변]" + vo.getAtitle() + "");
				System.out.println(vo.getAcontent());
				System.out.println("□작성일시:" + vo.getAwriteDate());
			}
		}
		System.out.println("=====================================");
	}

}
