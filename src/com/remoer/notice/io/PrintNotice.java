package com.remoer.notice.io;

import java.util.List;

import com.remoer.notice.vo.NoticeVO;

public class PrintNotice {

	public static void print(List<NoticeVO> list) {
		System.out.println("\n========  공지사항/이벤트 ==========");
		System.out.println(" 번호 | 제목");
		System.out.println("--------------------------------");
		if (list == null || list.size() == 0)
			System.out.println("* * * 작성된 공지사항이 없습니다. * * *");
		else {
			for (int i = 0; i < list.size(); i++) {
				NoticeVO vo = list.get(i);
				System.out.println(vo.getNo() + " | " + vo.getTitle());
			}
		}
		System.out.println("================================");
	}

	public static void print(NoticeVO vo) {
		if (vo == null) {
			System.out.println("-----------------------------------");
			System.out.println("** 해당 번호의 공지사항이 존재하지 않습니다. **");
		} else {
			System.out.println("\n------ " + vo.getTitle() + " ------");
			System.out.println(vo.getContent());
			System.out.println("□공지기간:" + vo.getStart_date() + "~" + vo.getEnd_date());
			System.out.println("□최종수정일:" + vo.getUpdate_date());
		}
		System.out.println("-----------------------------------");
	}

}
