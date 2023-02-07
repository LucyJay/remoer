package com.remoer.member.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.remoer.main.Check;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.member.io.PrintMember;
import com.remoer.member.service.MemberCheckIdServiceImpl;
import com.remoer.member.service.MemberFindIdServiceImpl;
import com.remoer.member.service.MemberJoinServiceImpl;
import com.remoer.member.service.MemberListServiceImpl;
import com.remoer.member.service.MemberLoginServiceImpl;
import com.remoer.member.service.MemberUpdateGradeServiceImpl;
import com.remoer.member.service.MemberUpdateInfoServiceImpl;
import com.remoer.member.service.MemberUpdatePwServiceImpl;
import com.remoer.member.service.MemberUpdateStatusServiceImpl;
import com.remoer.member.service.MemberViewServiceImpl;
import com.remoer.member.vo.LoginVO;

public class MemberController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				if (Main.login == null) {
					String[][] menus = { { "로그인", "회원가입", "아이디 찾기", "비밀번호 찾기" }, {}, {} };
					Out.menu("회원정보", 2, "이전 메뉴", menus);

					menu: switch (In.getStr("메뉴를 입력하세요")) {

					case "1":
						LoginVO login = new LoginVO();
						Out.lnTitleMini("로그인", 30);
						login.setId(In.getStr("아이디"));
						login.setPw(In.getStr("비밀번호"));
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
							String id = In.getStr("아이디");
							if ((boolean) Execute.run(new MemberCheckIdServiceImpl(), id)) {
								joinVO.setId(id);
								break;
							}
							Out.sys("중복되는 아이디가 존재합니다. 다른 아이디를 입력해 주세요.");
						}
						joinVO.setPw(In.getStr("비밀번호"));
						joinVO.setNickname(In.getStr("닉네임"));
						joinVO.setName(In.getStr("이름"));
						while (true) {
							try {
								String birth = In.getStr("생년월일(yyyy-mm-dd) : ");
								new SimpleDateFormat("yyyy-mm-dd").parse(birth);
								joinVO.setBirth(birth);
								break;
							} catch (Exception e) {
								Out.sys("형식에 맞게 다시 입력하세요.");
							}
						}
						joinVO.setAddress(In.getStr("주소"));
						while (true) {
							String tel = In.getStr("전화번호(000-0000-0000)");
							if (tel.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
								joinVO.setTel(tel);
								break;
							} else
								Out.sys("형식에 맞게 다시 입력하세요.");
						}
						joinVO.setEmail(In.getStr("이메일"));
						Out.sys("입력하신 정보로 회원가입을 진행하시겠습니까? (y / n)");
						while (true) {
							String join = In.getStr("");
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
						findVO.setName(In.getStr("이름"));
						while (true) {
							String tel = In.getStr("전화번호(000-0000-0000)");
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
						pwVO.setId(In.getStr("아이디"));
						pwVO.setName(In.getStr("이름"));
						while (true) {
							String tel = In.getStr("전화번호(000-0000-0000)");
							if (tel.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
								pwVO.setTel(tel);
								break;
							} else
								Out.sys("형식에 맞게 다시 입력하세요.");
						}
						Out.sys("비밀번호를 변경합니다. 새로운 비밀번호를 입력해 주세요.");
						pwVO.setPw(In.getStr(""));
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

					menu: switch (In.getStr("메뉴를 입력하세요")) {
					// 로그아웃
					case "1":
						Main.login = null;
						break;

					// 내 정보 보기
					case "2":
						// 본인 아이디로 ViewService 실행하여 정보 보기
						PrintMember.print((LoginVO) Execute.run(new MemberViewServiceImpl(), Main.login.getId()));
						break;

					// 내 정보 수정
					case "3":
						Out.lnTitleMini("내 정보 수정");
						LoginVO updateVO = Main.login; // 본인 정보로 된 LoginVO 객체 생성
						while (true) {
							Out.sys("1. 비밀번호 변경  2. 기타정보 수정  0. 취소");
							switch (In.getStr("")) {

							// 비밀번호 변경
							case "1":
								Out.titleMini("비밀번호 변경", 20);
								if (In.getStr("기존 비밀번호").equals(updateVO.getPw())) { // 비번 수정은 비밀번호 확인하고 진행
									updateVO.setPw(Check.newPw(false));
									if ((Integer) Execute.run(new MemberUpdatePwServiceImpl(), updateVO) == 1) {
										Out.sysln("비밀번호가 정상적으로 변경되었습니다.");
									} else {
										Out.sysln("비밀번호가 정상적으로 변경되지 않았습니다. 다시 시도해 주세요.");
									}
								} else {
									Out.sysln("비밀번호가 일치하지 않아 변경할 수 없습니다.");
								}
								break menu;

							// 회원정보 수정
							case "2":
								Out.titleMini("회원정보 수정", 20);
								while (true) {
									Out.sys("수정할 항목을 선택해 주세요.");
									Out.sys("1. 주소  2. 연락처  3. 이메일  9. 취소  0. 수정완료"); // 원하는 항목을 골라 아까 생성한 LoginVO 수정
									switch (In.getStr("")) {
									case "1":
										updateVO = write(updateVO, "address");
										break;
									case "2":
										updateVO = write(updateVO, "tel");
										break;
									case "3":
										updateVO = write(updateVO, "email");
										break;
									case "9":
										Out.sysln("이전으로 돌아갑니다.");
										break menu;
									case "0":
										PrintMember.print(updateVO);
										if (check()) {
											// 수정 완료된 LoginVO로 수정 실행
											if ((Integer) Execute.run(new MemberUpdateInfoServiceImpl(),
													updateVO) == 1) {
												Out.sysln("회원정보가 정상적으로 수정되었습니다.");
											} else {
												Out.sysln("회원정보가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
											}
										} else {
											Out.sysln("이전으로 돌아갑니다.");
										}
										break menu;
									default:
										Out.sys("1 ~ 3 또는 0만 입력 가능합니다.");
									}
								}
							case "0":
								Out.sysln("이전으로 돌아갑니다.");
								break menu;
							default:
								Out.sys("1 ~ 2 또는 0만 입력 가능합니다.");
							}
						}

						// 회원탈퇴
					case "4":
						Out.titleMini("회원탈퇴");
						Out.sys("탈퇴 후에는 같은 아이디로 재가입하실 수 없습니다.");
						Out.sys("정말 탈퇴하시려면 비밀번호를 입력해 주세요.");
						if (In.getStr("비밀번호").equals(Main.login.getPw())) { // 탈퇴 시 비밀번호 확인받음
							LoginVO withdrawVO = Main.login;
							withdrawVO.setStatus("탈퇴"); // 상태를 '탈퇴'로 세팅

							// 상태 수정 쿼리 실행
							if ((Integer) Execute.run(new MemberUpdateStatusServiceImpl(), withdrawVO) == 1) {
								Main.login = null;
								Out.sysln("정상적으로 탈퇴되었습니다.");
							} else {
								Out.sysln("회원탈퇴가 정상적으로 이루어지지 않았습니다. 다시 시도해 주세요.");
							}
						} else {
							Out.sysln("비밀번호가 일치하지 않습니다. 이전으로 돌아갑니다.");
						}
						break;

					case "0":
						Out.sysln("이전 메뉴로 돌아갑니다.");
						return;

					// 여기서부터 관리자만 실행 가능
					// 회원리스트
					case "5":
						if (Main.isAdmin()) {
							while (true) {
								Out.titleMini("회원리스트", 50);

								// 보고 싶은 리스트 종류를 매개변수로 보내 각각 다른 조건의 쿼리 실행
								Out.sys("1. 전체회원  2. 정상회원  3. 휴면회원  4. 탈퇴회원  0. 취소");
								switch (In.getStr("")) {
								case "1":
									PrintMember.print((List<LoginVO>) Execute.run(new MemberListServiceImpl(), null));
									break menu;
								case "2":
									PrintMember
											.print((List<LoginVO>) Execute.run(new MemberListServiceImpl(), "정상"));
									break menu;
								case "3":
									PrintMember.print((List<LoginVO>) Execute.run(new MemberListServiceImpl(), "휴면"));
									break menu;
								case "4":
									PrintMember.print(
											(List<LoginVO>) Execute.run(new MemberListServiceImpl(), "탈퇴"));
									break menu;
								case "0":
									Out.sysln("이전으로 돌아갑니다.");
									break menu;
								default:
									Out.sys("1 ~ 4 또는 0만 입력 가능합니다.");
								}
							}
						}

						// 회원정보 보기
					case "6":
						if (Main.isAdmin()) {
							Out.titleMini("회원정보 보기");
							PrintMember.print(

									// 관리자는 아이디를 입력해 ViewService 실행 가능
									(LoginVO) Execute.run(new MemberViewServiceImpl(), In.getStr("조회할 회원의 아이디")));
							break;
						}

						// 관리자 변경
					case "7":
						if (Main.isAdmin()) {
							LoginVO gradeVO = new LoginVO(); // 등급 변경에 쓸 LoginVO 객체 생성
							grade: while (true) {
								Out.titleMini("관리자 변경");
								Out.sys("1. 관리자 등록  2. 관리자 해제  0. 취소");
								switch (In.getStr("")) {

								// 관리자 등록 세팅
								case "1":
									gradeVO = write(gradeVO, "id"); // LoginVO에 아이디값 세팅

									// 입력한 아이디의 정보를 ViewService로 가져와 존재여부 및 등급 확인
									gradeVO = (LoginVO) Execute.run(new MemberViewServiceImpl(), gradeVO.getId());
									if (gradeVO == null) {
										Out.sysln("존재하지 않는 아이디입니다.");
										break menu;
									}
									if (gradeVO.getGrade_no() == 9) {
										Out.sysln("이미 관리자 등급인 회원입니다.");
										break menu;
									} else {
										gradeVO.setGrade_no(9); // 아이디가 존재하고 관리자 등급이 아닌 경우 LoginVO 객체에 등급값 9 세팅
										break grade;
									}

									// 관리자 해제 세팅
								case "2":
									String gid = In.getStr("아이디");
									if (gid.equals(Main.login.getId())) {
										Out.sysln("본인의 관리자 등급은 해제할 수 없습니다."); // 관리자 본인의 등급은 변경 불가
										break menu;
									}
									gradeVO.setId(gid);

									// 입력한 아이디의 정보를 ViewService로 가져와 존재여부 및 등급 확인
									gradeVO = (LoginVO) Execute.run(new MemberViewServiceImpl(), gradeVO.getId()); // 동일
									if (gradeVO == null) {
										Out.sysln("존재하지 않는 아이디입니다.");
										break menu;
									}
									if (gradeVO.getGrade_no() != 9) {
										Out.sysln("해당 회원은 관리자 등급이 아닙니다.");
										break menu;
									} else {
										gradeVO.setGrade_no(1); // 아이디가 존재하고 관리자 등급인 경우 LoginVO 객체에 등급값 1 세팅
										break grade;
									}
								case "0":
									Out.sysln("이전으로 돌아갑니다.");
									break menu;
								default:
									Out.sys("1 ~ 2 또는 0만 입력 가능합니다.");
								}
							}

							// 세팅한 대로 수정 실행
							if (check()) { // 진행여부 한 번 더 확인
								if ((Integer) Execute.run(new MemberUpdateGradeServiceImpl(), gradeVO) == 1) {
									Out.sysln("정상적으로 변경되었습니다.");
								} else {
									Out.sysln("회원등급이 정상적으로 변경되지 않았습니다. 다시 시도해 주세요.");
								}
							} else {
								Out.sysln("이전으로 돌아갑니다.");
							}
							break menu;

						}

						// 회원상태 변경(휴면해제, 강제탈퇴)
					case "8":
						if (Main.isAdmin()) {
							LoginVO statusVO = new LoginVO(); // 회원상태 변경을 위한 LoginVO 객체 생성
							status: while (true) {
								Out.titleMini("휴면/탈퇴 관리");
								Out.sys("1. 휴면해제  2. 강제탈퇴  0. 취소");
								switch (In.getStr("")) {

								// 휴면해제 세팅
								case "1":

									// 휴면해제 진행 시 휴면회원 리스트를 불러옴
									List<LoginVO> restList = (List<LoginVO>) Execute.run(new MemberListServiceImpl(),
											"rest");
									PrintMember.print(restList);
									if (restList == null) {
										Out.sysln("휴면회원이 존재하지 않아 이전으로 돌아갑니다."); // 휴면회원이 없으면 진행 취소
										break menu;
									}
									statusVO = write(statusVO, "id"); // 휴면해제할 아이디를 입력받음
									statusVO = (LoginVO) Execute.run(new MemberViewServiceImpl(), statusVO.getId());
									if (statusVO == null) {
										Out.sysln("존재하지 않는 아이디입니다.");
										break menu;
									}
									if (!(statusVO.getStatus().equals("휴면"))) {
										Out.sysln("휴면회원이 아닙니다.");
										break menu;
									} else {
										statusVO.setStatus("정상"); // 아이디가 존재하고 휴면회원인 경우 상태값을 '정상'으로 세팅
										break status;
									}

									// 강제탈퇴 세팅
								case "2":
									statusVO = write(statusVO, "id"); // 강제탈퇴할 아이디를 입력받음
									statusVO = (LoginVO) Execute.run(new MemberViewServiceImpl(), statusVO.getId());
									if (statusVO == null) {
										Out.sysln("존재하지 않는 아이디입니다.");
										break menu;
									}
									if (statusVO.getStatus().equals("탈퇴")) {
										Out.sysln("이미 탈퇴한 회원의 아이디입니다.");
										break menu;
									} else {
										statusVO.setStatus("탈퇴"); // 아이디가 존재하고 탈퇴회원이 아닌 경우 상태값을 '탈퇴'로 세팅
										break status;
									}
								case "0":
									Out.sysln("이전으로 돌아갑니다.");
									break menu;
								default:
									Out.sys("1 ~ 2 또는 0만 입력 가능합니다.");
								}
							}

							// 세팅한 대로 회원상태 변경 실행
							if (check()) {
								if ((Integer) Execute.run(new MemberUpdateStatusServiceImpl(), statusVO) == 1) {
									Out.sysln("정상적으로 변경되었습니다.");
								} else {
									Out.sysln("회원상태가 정상적으로 변경되지 않았습니다. 다시 시도해 주세요.");
								}
							} else {
								Out.sysln("이전으로 돌아갑니다.");
							}
							break menu;

						}
					default:
						Out.sysln("1 ~ " + (Main.isAdmin() ? "4" : "8") + "번 또는 0번만 입력할 수 있습니다. 다시 입력해 주세요.");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}

	}

	private LoginVO write(LoginVO vo, String... strs) { // 객체에 정보 입력하기 위한 메서드 : 간단한 조건 체크 들어있음
		for (String str : strs) {
			s: switch (str) {
			case "id":
				vo.setId(In.getStr("아이디"));
				break;
			case "pw":
				vo.setPw(In.getStr("비밀번호"));
				break;
			case "name":
				vo.setName(In.getStr("이름"));
				break;
			case "gender":
				while (true) {
					String gender = In.getStr("성별(남/여)");
					if (gender.equals("남") || gender.equals("여")) {
						vo.setGender(gender);
						break;
					} else {
						Out.sys("'남' 또는 '여'만 입력할 수 있습니다.");
					}
				}
				break;
			case "birth":
				while (true) {
					String birth = In.getStr("생년월일(yyyy-mm-dd)");
					if (Check.checkDate(birth)) { // 날짜 형식 yyyy-mm-dd인지 확인하는 메서드
						vo.setBirth(birth);
						break;
					}
				}
				break s;
			case "address":
				vo.setAddress(In.getStr("주소"));
				break;
			case "tel":
				while (true) {
					String tel = In.getStr("전화번호('-' 포함)");
					if (tel.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) { // 전화번호 정규표현식
						vo.setTel(tel);
						break;
					} else {
						Out.sys("형식에 맞게 입력해 주세요.");
					}
				}
				break;
			case "email":
//				while (true) {
				String email = In.getStr("이메일"); // 이메일 정규표현식 - 형식에 맞게 써도 자꾸 틀리다고 나와서 주석 처리함...
//					if (email.matches(
//							"^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$/i")) {
				vo.setEmail(email);
//						break;
//					} else {
//						Out.sys("형식에 맞게 입력해 주세요.");
//					}
//				}
				break;
			default:
				Out.err("개발오류"); // case에 존재하지 않는 매개변수를 넣어 이 메서드를 실행했을 시 출력
			}
		}
		return vo;
	}

	public boolean check() { // 진행 여부 체크 - 반복적으로 사용되어 메서드로 따로 뺐음
		while (true) {
			Out.sys("이대로 진행하시겠습니까? (y/n)");
			switch (In.getStr("")) {
			case "y":
				return true;
			case "n":
				return false;
			default:
				Out.sys("'y' 또는 'n'만 선택 가능합니다.");
			}
		}
	}

}
