package com.remoer.main;

import java.util.Scanner;

import com.remoer.notice.controller.NoticeController;

public class Main {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Login login = new Login();
		while (true) {
			System.out.println("----- 비회원 메인 -----");
			System.out.println("1. 공지사항/이벤트");
			System.out.println("2. 레시피 게시판");
			System.out.println("3. 로그인/회원가입");
			System.out.println("4. 고객센터");
			System.out.println("0. 종료");
			System.out.println("--------------------");

			String menu = scanner.nextLine();

			switch (menu) {
			case "1":
				new NoticeController().execute(login);
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
	}

}
