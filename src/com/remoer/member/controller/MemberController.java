package com.remoer.member.controller;

import java.text.SimpleDateFormat;

import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.member.service.MemberCheckIdServiceImpl;
import com.remoer.member.service.MemberFindIdServiceImpl;
import com.remoer.member.service.MemberJoinServiceImpl;
import com.remoer.member.service.MemberLoginServiceImpl;
import com.remoer.member.service.MemberUpdatePwServiceImpl;
import com.remoer.member.vo.LoginVO;

public class MemberController {
	public void execute() {
		while (true) {
			try {

				if (Main.login == null) {
					String[][] menus = { { "로그인", "회원가입", "아이디 찾기", "비밀번호 찾기" }, {}, {} };
					Out.menu("회원정보", 2, "이전 메뉴", menus);

					menu: switch (In.inStr("메뉴를 입력하세요")) {

					case "1":
						LoginVO login = new LoginVO();
						Out.lnTitleMini("로그인", 30);
						login.setId(In.inStr("아이디"));
						login.setPw(In.inStr("비밀번호"));
						login = (LoginVO) Execute.run(new MemberLoginServiceImpl(), login);
						if (login == null)
							Out.sysln("로그인에 실패했습니다. 아이디와 비밀번호를 확인해 주세요.");
						else {
							Out.sysln("로그인에 성공했습니다.");
							Main.login = login;
						}
						break;
					case "2":
						Out.lnTitleMini("회원가입", 30);
						LoginVO joinVO = new LoginVO();
						while (true) {
							String id = In.inStr("아이디");
							if ((boolean) Execute.run(new MemberCheckIdServiceImpl(), id)) {
								joinVO.setId(id);
								break;
							}
							Out.sys("중복되는 아이디가 존재합니다. 다른 아이디를 입력해 주세요.");
						}
						joinVO.setPw(In.inStr("비밀번호"));
						joinVO.setName(In.inStr("이름"));
						while (true) {
							try {
								String birth = In.inStr("생년월일(yyyy-mm-dd) : ");
								new SimpleDateFormat("yyyy-mm-dd").parse(birth);
								joinVO.setBirth(birth);
								break;
							} catch (Exception e) {
								Out.sys("형식에 맞게 다시 입력하세요.");
							}
						}
						joinVO.setAddress(In.inStr("주소"));
						while (true) {
							String tel = In.inStr("전화번호(000-0000-0000)");
							if (tel.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
								joinVO.setTel(tel);
								break;
							} else
								Out.sys("형식에 맞게 다시 입력하세요.");
						}
						joinVO.setEmail(In.inStr("이메일"));
						Out.sys("입력하신 정보로 회원가입을 진행하시겠습니까? (y / n)");
						while (true) {
							String join = In.inStr("");
							if (join.equals("y"))
								break;
							else if (join.equals("n")) {
								Out.sysln("회원가입을 취소합니다.");
								break menu;
							} else
								Out.sys("y 또는 n만 입력할 수 있습니다.");
						}
						if ((Integer) Execute.run(new MemberJoinServiceImpl(), joinVO) == 1) {
							Out.sysln("회원가입에 성공했습니다. Remoer 가입을 축하합니다! ^^");
						} else {
							Out.sysln("회원가입에 실패했습니다. 정보를 확인하고 다시 시도해 주세요.");
						}
						break;
					case "3":
						Out.lnTitleMini("아이디 찾기", 30);
						LoginVO findVO = new LoginVO();
						findVO.setName(In.inStr("이름"));
						while (true) {
							String tel = In.inStr("전화번호(000-0000-0000)");
							if (tel.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
								findVO.setTel(tel);
								break;
							} else
								Out.sys("형식에 맞게 다시 입력하세요.");
						}
						Object result = Execute.run(new MemberFindIdServiceImpl(), findVO);
						if (result == null) {
							Out.sysln("일치하는 아이디가 없습니다. 먼저 회원가입을 진행해 주세요.");
						} else
							Out.sysln("회원님의 아이디는 " + result + "입니다.");
						break;
					case "4":
						Out.lnTitleMini("비밀번호 찾기", 30);
						LoginVO pwVO = new LoginVO();
						pwVO.setId(In.inStr("아이디"));
						pwVO.setName(In.inStr("이름"));
						while (true) {
							String tel = In.inStr("전화번호(000-0000-0000)");
							if (tel.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
								pwVO.setTel(tel);
								break;
							} else
								Out.sys("형식에 맞게 다시 입력하세요.");
						}
						Out.sys("비밀번호를 변경합니다. 새로운 비밀번호를 입력해 주세요.");
						pwVO.setPw(In.inStr(""));
						if ((Integer) Execute.run(new MemberUpdatePwServiceImpl(), pwVO) == 1) {
							Out.sysln("비밀번호가 변경되었습니다. 변경된 비밀번호로 로그인해 주세요.");
						} else {
							Out.sys("일치하는 정보가 없습니다.");
							Out.sysln("가입하실 때 입력한 아이디와 이름, 전화번호를 다시 확인해 주세요.");
						}
						break;
					case "0":
						Out.sysln("이전 메뉴로 돌아갑니다.");
						return;
					default:
						Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

					}
				} else {
					String[][] menus = { {}, { "로그아웃", "내 정보 보기", "내 정보 수정", "회원탈퇴" },
							{ "회원리스트", "회원정보 보기", "회원정보 수정", "회원삭제" } };
					Out.menu("회원정보", 4, "이전 메뉴", menus);

					switch (In.inStr("메뉴를 입력하세요")) {
					case "1":
						Main.login = null;
						break;
					case "2":
						break;
					case "3":
						break;
					case "4":
						break;
					case "5":
						if (Main.isAdmin()) {
							break;
						}
					case "6":
						if (Main.isAdmin()) {
							break;
						}
					case "7":
						if (Main.isAdmin()) {
							break;
						}
					case "8":
						if (Main.isAdmin()) {
							break;
						}
					case "0":
						Out.sysln("이전 메뉴로 돌아갑니다.");
						return;
					default:
						Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}
	}

}
