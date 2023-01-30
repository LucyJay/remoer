package com.remoer.qna.controller;

import java.util.List;

import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.qna.io.PrintQna;
import com.remoer.qna.service.QnaDeleteAnswerServiceImpl;
import com.remoer.qna.service.QnaDeleteQuestionServiceImpl;
import com.remoer.qna.service.QnaListServiceImpl;
import com.remoer.qna.service.QnaUpdateAnswerServiceImpl;
import com.remoer.qna.service.QnaUpdateQuestionServiceImpl;
import com.remoer.qna.service.QnaViewServiceImpl;
import com.remoer.qna.service.QnaWriteAnswerServiceImpl;
import com.remoer.qna.service.QnaWriteQuestionServiceImpl;
import com.remoer.qna.vo.QnaVO;

public class QnaController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { { "문의목록", "문의/답변보기" }, { "문의하기", "문의수정", "문의삭제" }, { "답변하기", "답변수정", "답변삭제" } };
				Out.menu("고객센터", 4, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					list: while (true) {
						Out.sys("1. 전체문의목록  2. 내가 쓴 문의");
						switch (In.getStr("")) {
						case "1":
							PrintQna.print((List<QnaVO>) Execute.run(new QnaListServiceImpl(), null));
							break list;
						case "2":
							PrintQna.print((List<QnaVO>) Execute.run(new QnaListServiceImpl(), Main.login.getId()));
							break list;
						}
					}
				case "2":
					Long viewNo = In.getLong("확인할 글번호");
					QnaVO viewVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), viewNo);
					PrintQna.print(viewVO);
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "3":
					if (Main.login != null) {
						Out.titleMini("문의하기", 30);
						QnaVO writeVO = new QnaVO();
						writeVO.setQtitle(In.getStr("제목"));
						writeVO.setQcontent(In.getStr("내용"));
						writeVO.setWriter(Main.login.getId());
						if ((Integer) Execute.run(new QnaWriteQuestionServiceImpl(), writeVO) == 1) {
							Out.sysln("질문이 정상적으로 등록되었습니다.");
						} else
							Out.sysln("질문이 정상적으로 등록되지 않았습니다. 다시 시도해 주세요.");
						break;
					}
				case "4":
					if (Main.login != null) {
						Out.titleMini("문의수정", 30);
						Out.sys("아직 답변이 등록되지 않은 질문만 수정할 수 있습니다.");
						Long updateNo = In.getLong("수정할 글번호");
						QnaVO updateVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), updateNo);
						PrintQna.print(updateVO);
						update: while (true) {
							Out.sys("수정할 항목을 선택하세요.");
							Out.sys("1. 제목  2. 내용  9. 취소  0. 수정완료");
							switch (In.getStr("")) {
							case "1":
								updateVO.setQtitle(In.getStr("제목"));
								break;
							case "2":
								updateVO.setQcontent(In.getStr("내용"));
								break;
							case "9":
								Out.sysln("수정을 취소하고 이전으로 돌아갑니다.");
								break update;
							case "0":
								if ((Integer) Execute.run(new QnaUpdateQuestionServiceImpl(), updateVO) == 1) {
									Out.sysln("문의글이 정상적으로 수정되었습니다.");
								} else
									Out.sysln("문의글이 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
								break update;
							default:
								Out.sys("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
							}
						}
						break;
					}
				case "5":
					if (Main.login != null) {
						Out.titleMini("문의삭제", 30);
						Long deleteNo = In.getLong("삭제할 글번호");
						if ((Integer) Execute.run(new QnaDeleteQuestionServiceImpl(), deleteNo) == 1) {
							Out.sysln("문의글이 정상적으로 삭제되었습니다.");
						} else
							Out.sysln("문의글이 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
						break;
					}
				case "6":
					if (Main.isAdmin()) {
						Out.titleMini("답변하기", 30);
						Long answerNo = In.getLong("답변할 글번호");
						QnaVO answerVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), answerNo);
						if (answerVO.getAtitle() == null) {
							PrintQna.print(answerVO);
							answerVO.setAtitle(In.getStr("제목"));
							answerVO.setAcontent(In.getStr("내용"));
							if ((Integer) Execute.run(new QnaWriteAnswerServiceImpl(), answerVO) == 1) {
								Out.sysln("답변이 정상적으로 등록되었습니다.");
							} else
								Out.sysln("답변이 정상적으로 등록되지 않았습니다. 다시 시도해 주세요.");
							break;
						} else {
							Out.sysln("이미 답변이 등록된 질문입니다. 답변수정 기능을 이용해 주세요.");
						}
					}
				case "7":
					if (Main.isAdmin()) {
						Out.titleMini("답변수정", 30);
						Long updateANo = In.getLong("수정할 글번호");
						QnaVO updateAVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), updateANo);
						PrintQna.print(updateAVO);
						update: while (true) {
							Out.sys("수정할 항목을 선택하세요.");
							Out.sys("1. 제목  2. 내용  9. 취소  0. 수정완료");
							switch (In.getStr("")) {
							case "1":
								updateAVO.setAtitle(In.getStr("제목"));
								break;
							case "2":
								updateAVO.setAcontent(In.getStr("내용"));
								break;
							case "9":
								Out.sysln("수정을 취소하고 이전으로 돌아갑니다.");
								break update;
							case "0":
								if ((Integer) Execute.run(new QnaUpdateAnswerServiceImpl(), updateAVO) == 1) {
									Out.sysln("답변이 정상적으로 수정되었습니다.");
								} else
									Out.sysln("답변이 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
								break update;
							default:
								Out.sys("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
							}
						}
						break;
					}
				case "8":
					if (Main.isAdmin()) {
						Out.titleMini("답변삭제", 30);
						Long deleteNo = In.getLong("답변을 삭제할 글번호");
						if ((Integer) Execute.run(new QnaDeleteAnswerServiceImpl(), deleteNo) == 1) {
							Out.sysln("답변이 정상적으로 삭제되었습니다.");
						} else
							Out.sysln("답변이 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
						break;
					}
				default:
					Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");

				}

			} catch (Exception e) {
				e.printStackTrace();
				Out.err("오류가 발생했습니다.", "다시 시도해 주세요.", "문의: 관리자(admin@remoer.com)");
			}
		}
	}
}
