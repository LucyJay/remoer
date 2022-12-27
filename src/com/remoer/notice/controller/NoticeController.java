package com.remoer.notice.controller;

import com.remoer.main.Login;
import com.remoer.main.Main;

public class NoticeController {
	public void execute(Login login) {
		while (true) {
			try {
				if (login.getGrade().equals("비회원") || login.getGrade().equals("일반회원")) {
					System.out.println("----- 공지사항 -----");
					System.out.println("1. 공지사항 리스트");
					System.out.println("2. 공지사항 읽기");
					System.out.println("0. 이전 메뉴");
					System.out.println("--------------------");

					String menu = Main.scanner.nextLine();

					switch (menu) {
					case "1":
						//1. 현재공지 2. 지난공지 선택
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						break;
					case "0":
						System.out.println("프로그램을 종료합니다.");
						System.exit(0);
					default:
						System.out.println("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

					}
				} else if (login.getGrade().equals("관리자")) {
					System.out.println("----- 공지사항 -----");
					System.out.println("1. 공지사항 리스트");
					System.out.println("2. 공지사항 읽기");
					System.out.println("3. 공지사항 등록");
					System.out.println("4. 공지사항 수정");
					System.out.println("5. 공지사항 삭제");
					System.out.println("0. 이전 메뉴");
					System.out.println("--------------------");

					String menu = Main.scanner.nextLine();

					switch (menu) {
					case "1":
						//1. 현재공지 2. 지난공지 3. 미래공지 선택
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						break;
					case "0":
						System.out.println("프로그램을 종료합니다.");
						System.exit(0);
					default:
						System.out.println("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("오류가 발생했습니다. 다시 시도해 주세요.");
			}
		}
	}
}
