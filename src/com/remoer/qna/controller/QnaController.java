package com.remoer.qna.controller;

import java.util.List;

import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.qna.io.PrintQna;
import com.remoer.qna.service.QnaDeleteAnswerServiceImpl;
import com.remoer.qna.service.QnaDeleteQuestionServiceImpl;
import com.remoer.qna.service.QnaFaqDeleteServiceImpl;
import com.remoer.qna.service.QnaFaqListServiceImpl;
import com.remoer.qna.service.QnaFaqUpdateServiceImpl;
import com.remoer.qna.service.QnaFaqViewServiceImpl;
import com.remoer.qna.service.QnaFaqWriteServiceImpl;
import com.remoer.qna.service.QnaListServiceImpl;
import com.remoer.qna.service.QnaUpdateAnswerServiceImpl;
import com.remoer.qna.service.QnaUpdateQuestionServiceImpl;
import com.remoer.qna.service.QnaViewServiceImpl;
import com.remoer.qna.service.QnaWriteAnswerServiceImpl;
import com.remoer.qna.service.QnaWriteQuestionServiceImpl;
import com.remoer.qna.vo.FaqVO;
import com.remoer.qna.vo.QnaVO;

public class QnaController {
	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { { "FAQ", "문의목록", "문의/답변보기" }, { "문의하기", "문의수정", "문의삭제" },
						{ "답변하기", "답변수정", "답변삭제", "FAQ관리" } };
				Out.menu("고객센터", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					PrintQna.printFaq((List<FaqVO>) Execute.run(new QnaFaqListServiceImpl(), null));
					break;
				case "2":
					list: while (true) {
						Out.sys("1. 전체문의목록  2. 내가 쓴 문의");
						switch (In.getStr("")) {
						case "1":
							PrintQna.print((List<QnaVO>) Execute.run(new QnaListServiceImpl(), null));
							break list;
						case "2":
							if (Main.login == null) {
								Out.sysln("로그인 후 이용하실 수 있습니다.");
							} else {
								PrintQna.print((List<QnaVO>) Execute.run(new QnaListServiceImpl(), Main.login.getId()));
							}
							break list;
						default:
							Out.sys("1 또는 2만 입력하실 수 있습니다.");
						}
					}
					break;
				case "3":
					Long viewNo = In.getLong("확인할 글번호");
					QnaVO viewVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), viewNo);
					PrintQna.print(viewVO);
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "4":
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
				case "5":
					if (Main.login != null) {
						Out.titleMini("문의수정", 30);
						Long updateNo = In.getLong("수정할 글번호");
						QnaVO updateVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), updateNo);
						PrintQna.print(updateVO);
						if (updateVO != null) {
							if (!updateVO.isAnswer()) {
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
							} else {
								Out.sys("이미 답변이 등록되었습니다. 답변이 등록되지 않은 문의글만 수정할 수 있습니다.");
							}
						}
						break;
					}
				case "6":
					if (Main.login != null) {
						Out.titleMini("문의삭제", 30);
						Long deleteNo = In.getLong("삭제할 글번호");
						QnaVO deleteVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), deleteNo);
						if (deleteVO != null) {
							if (deleteVO.getWriter().equals(Main.login.getId())) {
								if ((Integer) Execute.run(new QnaDeleteQuestionServiceImpl(), deleteNo) == 1) {
									Out.sysln("문의글이 정상적으로 삭제되었습니다.");
								} else
									Out.sysln("문의글이 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
							} else {
								Out.sysln("본인이 등록한 문의만 삭제할 수 있습니다.");
							}
						} else {
							Out.sysln("존재하지 않는 글번호입니다.");
						}
						break;
					}
				case "7":
					if (Main.isAdmin()) {
						Out.titleMini("답변하기", 30);
						Long answerNo = In.getLong("답변할 글번호");
						QnaVO answerVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), answerNo);
						if (!answerVO.isAnswer()) {
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
				case "8":
					if (Main.isAdmin()) {
						Out.titleMini("답변수정", 30);
						Long updateANo = In.getLong("수정할 글번호");
						QnaVO updateAVO = (QnaVO) Execute.run(new QnaViewServiceImpl(), updateANo);
						PrintQna.print(updateAVO);
						if (updateAVO != null) {
							if (updateAVO.isAnswer()) {
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
							} else {
								Out.sysln("답변이 등록되지 않아 수정할 수 없습니다. 답변등록 메뉴를 이용해 주세요.");
							}
						} else {
							Out.sysln("존재하지 않는 글번호입니다.");
						}
						break;
					}
				case "9":
					if (Main.isAdmin()) {
						Out.titleMini("답변삭제", 30);
						Long deleteNo = In.getLong("답변을 삭제할 글번호");
						if ((Integer) Execute.run(new QnaDeleteAnswerServiceImpl(), deleteNo) == 1) {
							Out.sysln("답변이 정상적으로 삭제되었습니다.");
						} else
							Out.sysln("답변이 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
						break;
					}
				case "10":
					if (Main.isAdmin()) {
						Out.titleMini("FAQ관리", 30);
						faq: while (true) {
							Out.sys("1. FAQ 등록  2. FAQ 수정  3. FAQ 삭제  0. 이전 메뉴");
							switch (In.getStr("")) {
							case "1":
								FaqVO writeFaq = new FaqVO();
								writeFaq.setTitle(In.getStr("제목"));
								writeFaq.setContent(In.getStr("질문내용"));
								writeFaq.setAnswer(In.getStr("답변내용"));
								if ((Integer) Execute.run(new QnaFaqWriteServiceImpl(), writeFaq) == 1) {
									Out.sysln("FAQ가 정상적으로 등록되었습니다.");
								} else
									Out.sysln("FAQ가 정상적으로 등록되지 않았습니다. 다시 시도해 주세요.");
								break faq;
							case "2":
								Long updateFNo = In.getLong("수정할 FAQ번호");
								FaqVO updateFaq = (FaqVO) Execute.run(new QnaFaqViewServiceImpl(), updateFNo);
								if (updateFaq == null) {
									Out.sysln("존재하지 않는 FAQ번호입니다.");
								} else {
									PrintQna.printFaq(updateFaq);
									updatefaq: while (true) {
										Out.sys("수정할 항목을 선택하세요.");
										Out.sys("1. 제목  2. 질문내용  3. 답변내용 9. 취소  0. 수정완료");
										switch (In.getStr("")) {
										case "1":
											updateFaq.setTitle(In.getStr("제목"));
											break;
										case "2":
											updateFaq.setContent(In.getStr("질문내용"));
											break;
										case "3":
											updateFaq.setAnswer(In.getStr("답변내용"));
											break;
										case "9":
											Out.sysln("수정을 취소하고 이전으로 돌아갑니다.");
											break updatefaq;
										case "0":
											if ((Integer) Execute.run(new QnaFaqUpdateServiceImpl(), updateFaq) == 1) {
												Out.sysln("FAQ가 정상적으로 수정되었습니다.");
											} else
												Out.sysln("FAQ가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
											break updatefaq;
										default:
											Out.sys("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
										}
									}
								}
								break faq;
							case "3":
								Long deleteFNo = In.getLong("삭제할 FAQ번호");
								if ((Integer) Execute.run(new QnaFaqDeleteServiceImpl(), deleteFNo) == 1) {
									Out.sysln("FAQ가 정상적으로 삭제되었습니다.");
								} else
									Out.sysln("FAQ가 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
								break faq;
							case "0":
								Out.sysln("이전 메뉴로 돌아갑니다.");
								break faq;
							default:
								Out.sys("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
							}
						}
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
