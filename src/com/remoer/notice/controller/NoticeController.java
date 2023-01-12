package com.remoer.notice.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Out;
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
				String[][] menus = { { "리스트", "읽기"}, {}, {"등록", "수정", "삭제"} };
				Out.menu("공지사항/이벤트", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {
				case "1":
					String period;
					// 1. 현재공지 2. 지난공지 선택
					while (true) {
						Out.sys("1. 현재 공지  2. 지난 공지");
						period = In.getStr("");
						if (period.equals("1") || period.equals("2"))
							break;
						Out.sysln("잘못 입력하셨습니다.");
					}
					PrintNotice.print((List<NoticeVO>) Execute.run(new NoticeListServiceImpl(), period));
					break;
				case "2":
					Long viewNo = In.getLong("확인할 공지번호");
					PrintNotice.print((NoticeVO) Execute.run(new NoticeViewServiceImpl(), viewNo));
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "3":
					NoticeVO writeVO = new NoticeVO();
					Out.lnTitleMini("공지사항 등록", 30);
					writeVO.setTitle(In.getStr("제목"));
					writeVO.setContent(In.getStr("내용"));
					// 1. 미지정 2. 시작날짜지정 3. 종료일 지정 4. 시작/종료 모두 지정
					DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
					Out.sys("공지사항의 시작일을 지정해 주세요. (yyyy-mm-dd, 형식과 다를 시 오늘로 지정)");
					String start = In.getStr("");
					try {
						df.parse(start);
						writeVO.setStart_date(start);
					} catch (Exception e) {
					}
					Out.sys("공지사항의 종료일을 지정해 주세요. (yyyy-mm-dd, 형식과 다를 시 9999-12-31로 지정)");
					String end = In.getStr("");
					try {
						df.parse(end);
						writeVO.setEnd_date(end);
					} catch (Exception e) {
					}
					if ((Integer) Execute.run(new NoticeWriteServiceImpl(), writeVO) == 1) {
						Out.sysln("공지사항이 정상적으로 등록되었습니다.");
					} else {
						Out.sysln("공지사항이 정상적으로 등록되지 않았습니다.");
					}
					break;
				case "4":
					Long updateNo = In.getLong("확인할 공지번호");
					NoticeVO updateVO = (NoticeVO) Execute.run(new NoticeViewServiceImpl(), updateNo);
					PrintNotice.print(updateVO);
					if (updateVO != null) {
						Out.lnTitleMini("공지사항 수정", 30);
						update: while (true) {
							Out.sys("1. 제목  2. 내용  3. 시작일  4. 종료일  9. 취소  0. 수정완료");
							String upd = In.getStr("");
							switch (upd) {
							case "1":
								updateVO.setTitle(In.getStr("제목"));
								break;
							case "2":
								updateVO.setContent(In.getStr("내용"));
								break;
							case "3":
								DateFormat dfs = new SimpleDateFormat("yyyy-mm-dd");
								String startUp = In.getStr("시작일(yyyy-mm-dd)");
								try {
									dfs.parse(startUp);
									updateVO.setStart_date(startUp);
								} catch (Exception e) {
								}
								break;
							case "4":
								DateFormat dfe = new SimpleDateFormat("yyyy-mm-dd");
								String endUp = In.getStr("종료일(yyyy-mm-dd)");
								try {
									dfe.parse(endUp);
									updateVO.setEnd_date(endUp);
								} catch (Exception e) {
								}
								break;
							case "9":
								Out.sysln("수정을 취소합니다.");
								break notice;
							case "0":
								if ((Integer) Execute.run(new NoticeUpdateServiceImpl(), updateVO) == 1)
									Out.sysln("공지사항이 정상적으로 수정되었습니다.");
								else
									Out.sysln("공지사항이 정상적으로 수정되지 않았습니다.");
								break update;
							default:
								Out.sysln("잘못 입력하셨습니다. 번호를 확인해 주세요.");
							}
						}
					}
					break;
				case "5" :
					Long deleteNo = In.getLong("삭제할 공지번호");
					if ((Integer) Execute.run(new NoticeViewServiceImpl(), deleteNo) == 1)
						Out.sysln("공지사항이 정상적으로 삭제되었습니다.");
					else
						Out.sysln("공지사항이 정상적으로 삭제되지 않았습니다.");
				default:
					Out.sysln("잘못 입력하셨습니다. 메뉴번호를 확인해 주세요.");

				}

			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}
	}
}
