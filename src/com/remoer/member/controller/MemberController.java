package com.remoer.member.controller;

import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.member.service.MemberLoginServiceImpl;
import com.remoer.member.vo.LoginVO;
import com.remoer.notice.controller.NoticeController;

public class MemberController {
	public void execute() {
		while (true) {
			try {
				if (Main.login == null) {
					System.out.println("\n\n========= 레시피로 모인 사람들 REMOER ===========");
					System.out.println("-------- Remoer 회원관리 ----------");
					System.out.println("1. 로그인  2. 회원가입");
					System.out.println("3. 아이디 찾기  4. 비밀번호 찾기  0. 이전 메뉴");
					System.out.println("--------------------------------------------");
					String menu = In.inStr("메뉴를 입력하세요 ▶");

					switch (menu) {
					case "1":
						LoginVO login = new LoginVO();
						System.out.println("---<< 로그인 >>---");
						login.setId(In.inStr("아이디 : "));
						login.setPw(In.inStr("비밀번호 : "));
						login = (LoginVO) Execute.run(new MemberLoginServiceImpl(), login);
						if (login == null)
							System.out.println("[로그인에 실패했습니다. 아이디와 비밀번호를 확인해 주세요.]");
						else {
							System.out.println("[로그인에 성공했습니다.]");
							Main.login = login;
						}

						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						break;
					case "0":
						System.out.println("[이전 메뉴로 돌아갑니다.]");
						return;
					default:
						System.out.println("[잘못 누르셨습니다. 메뉴번호를 확인해 주세요.]");

					}
				} else {
					System.out.println("\n========= 레시피로 모인 사람들 REMOER ===========");
					System.out.println("----- Remoer 회원관리 : "+Main.login.getName()+"님 -----");
					System.out.println("1. 로그아웃  2. 회원가입");
					System.out.println("3. 아이디 찾기  4. 비밀번호 찾기  0. 이전 메뉴");
					System.out.println("--------------------------------------------");
					String menu = In.inStr("메뉴를 입력하세요");

					switch (menu) {
					case "1":
						Main.login = null;
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						break;
					case "0":
						System.out.println("[이전 메뉴로 돌아갑니다.]");
						return;
					default:
						System.out.println("[잘못 누르셨습니다. 메뉴번호를 확인해 주세요.]");

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[오류가 발생했습니다. 다시 시도해 주세요.]");
			}
		}
	}

}
