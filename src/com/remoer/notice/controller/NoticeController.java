package com.remoer.notice.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.notice.io.PrintNotice;
import com.remoer.notice.service.NoticeListServiceImpl;
import com.remoer.notice.service.NoticeUpdateServiceImpl;
import com.remoer.notice.service.NoticeViewServiceImpl;
import com.remoer.notice.service.NoticeWriteServiceImpl;
import com.remoer.notice.vo.NoticeVO;

public class NoticeController {
	@SuppressWarnings("unchecked")
	public void execute() {
		notice: while (true) {
			try {
				System.out.println("\n\n----- Remoer 공지사항/이벤트 -----");
				System.out.println("1. 리스트  2. 읽기");
				if (Main.isAdmin()) {
					System.out.println("3. 등록  4. 수정  5. 삭제");
				}
				System.out.println("0. 이전 메뉴");
				System.out.println("--------------------");

				String menu = In.inStr("메뉴를 입력하세요 ▶ ");

				switch (menu) {
				case "1":
					String period;
					// 1. 현재공지 2. 지난공지 선택
					while (true) {
						System.out.println("\n[1. 현재 공지  2. 지난 공지]");
						period = In.inStr("▶ ");
						if (period.equals("1") || period.equals("2"))
							break;
						System.out.println("[잘못 입력하셨습니다.]");
					}
					PrintNotice.print((List<NoticeVO>) Execute.run(new NoticeListServiceImpl(), period));
					break;
				case "2":
					Long viewNo = In.inLong("확인할 공지번호 ▶");
					PrintNotice.print((NoticeVO) Execute.run(new NoticeViewServiceImpl(), viewNo));
					break;
				case "0":
					System.out.println("[이전 메뉴로 돌아갑니다.]");
					return;
				case "3":
					NoticeVO writeVO = new NoticeVO();
					System.out.println("\n---<<공지 등록>>---");
					writeVO.setTitle(In.inStr("제목 ▶ "));
					writeVO.setContent(In.inStr("내용 ▶ "));
					// 1. 미지정 2. 시작날짜지정 3. 종료일 지정 4. 시작/종료 모두 지정
					DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
					System.out.println("공지사항의 시작일을 지정해 주세요. (yyyy-mm-dd, 형식과 다를 시 오늘로 지정)");
					String start = In.inStr("▶ ");
					try {
						df.parse(start);
						writeVO.setStart_date(start);
					} catch (Exception e) {
					}
					System.out.println("공지사항의 종료일을 지정해 주세요. (yyyy-mm-dd, 형식과 다를 시 9999-12-31)");
					String end = In.inStr("▶ ");
					try {
						df.parse(end);
						writeVO.setEnd_date(end);
					} catch (Exception e) {
					}
					if ((Integer) Execute.run(new NoticeWriteServiceImpl(), writeVO) == 1) {
						System.out.println("공지사항이 정상적으로 등록되었습니다.");
					} else {
						System.out.println("공지사항이 정상적으로 등록되지 않았습니다.");
					}
					break;
				case "4":
					Long updateNo = In.inLong("확인할 공지번호 ▶");
					NoticeVO updateVO = (NoticeVO) Execute.run(new NoticeViewServiceImpl(), updateNo);
					PrintNotice.print(updateVO);
					if (updateVO != null) {
						System.out.println("\n---<<공지 수정>>---");
						update: while (true) {
							System.out.println("\n1. 제목  2. 내용  3. 시작일  4. 종료일  9. 취소  0. 수정완료");
							String upd = In.inStr("▶ ");
							switch (upd) {
							case "1":
								updateVO.setTitle(In.inStr("제목 ▶ "));
								break;
							case "2":
								updateVO.setContent(In.inStr("내용 ▶ "));
								break;
							case "3":
								DateFormat dfs = new SimpleDateFormat("yyyy-mm-dd");
								String startUp = In.inStr("시작일(yyyy-mm-dd) ▶ ");
								try {
									dfs.parse(startUp);
									updateVO.setStart_date(startUp);
								} catch (Exception e) {
								}
								break;
							case "4":
								DateFormat dfe = new SimpleDateFormat("yyyy-mm-dd");
								String endUp = In.inStr("종료일(yyyy-mm-dd) ▶ ");
								try {
									dfe.parse(endUp);
									updateVO.setEnd_date(endUp);
								} catch (Exception e) {
								}
								break;
							case "9":
								System.out.println("[수정을 취소합니다.]");
								break notice;
							case "0":
								if ((Integer) Execute.run(new NoticeUpdateServiceImpl(), updateVO) == 1)
									System.out.println("공지사항이 정상적으로 수정되었습니다.");
								else
									System.out.println("공지사항이 정상적으로 수정되지 않았습니다.");
								break update;
							default:
								System.out.println("[잘못 입력하셨습니다.]");
							}
						}
					}
					break;
				case "5" :
					Long deleteNo = In.inLong("삭제할 공지번호 ▶");
					if ((Integer) Execute.run(new NoticeViewServiceImpl(), deleteNo) == 1)
						System.out.println("공지사항이 정상적으로 삭제되었습니다.");
					else
						System.out.println("공지사항이 정상적으로 삭제되지 않았습니다.");
				default:
					System.out.println("[잘못 누르셨습니다. 메뉴번호를 확인해 주세요.]");

				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[오류가 발생했습니다. 다시 시도해 주세요.]");
			}
		}
	}
}
