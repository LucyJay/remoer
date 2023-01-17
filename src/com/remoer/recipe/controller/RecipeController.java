package com.remoer.recipe.controller;

import java.util.ArrayList;
import java.util.List;

import com.remoer.ingredient.vo.IngredientVO;
import com.remoer.main.Check;
import com.remoer.main.Execute;
import com.remoer.main.In;
import com.remoer.main.Main;
import com.remoer.main.Out;
import com.remoer.recipe.io.PrintRecipe;
import com.remoer.recipe.service.RecipeCheckReplyWriterServiceImpl;
import com.remoer.recipe.service.RecipeCheckWriterServiceImpl;
import com.remoer.recipe.service.RecipeDeleteServiceImpl;
import com.remoer.recipe.service.RecipeDestarServiceImpl;
import com.remoer.recipe.service.RecipeListServiceImpl;
import com.remoer.recipe.service.RecipeMyListServiceImpl;
import com.remoer.recipe.service.RecipeStarServiceImpl;
import com.remoer.recipe.service.RecipeUpdateReplyServiceImpl;
import com.remoer.recipe.service.RecipeUpdateServiceImpl;
import com.remoer.recipe.service.RecipeViewServiceImpl;
import com.remoer.recipe.service.RecipeWriteServiceImpl;
import com.remoer.recipe.vo.RecipeVO;
import com.remoer.recipe.vo.ReplyVO;
import com.remoer.recipe.vo.StarVO;

public class RecipeController {

	@SuppressWarnings("unchecked")
	public void execute() {
		while (true) {
			try {

				String[][] menus = { { "이 달의 Best", "레시피리스트", "레시피 보기" }, { "레시피 올리기", "내 레시피", "레시피 수정", "레시피 삭제" },
						{} };
				Out.menu("레시피나눔", 3, "이전 메뉴", menus);

				switch (In.getStr("메뉴를 입력하세요")) {

				case "1":
					PrintRecipe.print((List<RecipeVO>) Execute.run(new RecipeListServiceImpl(), true));
					break;
				case "2":
					PrintRecipe.print((List<RecipeVO>) Execute.run(new RecipeListServiceImpl(), false));
					break;
				case "3":
					Long viewNo = In.getLong("확인할 글번호");
					RecipeVO viewVO = (RecipeVO) Execute.run(new RecipeViewServiceImpl(), viewNo);
					PrintRecipe.print(viewVO);
					if (Main.login != null && viewVO != null) {
						view: while (true) {
							Out.lineln("-", 30);
							String[][] viewMenus = { {}, { "별점 등록", "별점 취소", "댓글 등록", "댓글 수정", "댓글 삭제" }, {} };
							Out.menuNum("이전 메뉴", 5, viewMenus);
							Out.lineln("-", 30);
							switch (In.getStr()) {
							case "1":
								StarVO newStar = new StarVO();
								int star = 0;
								while (true) {
									Out.sys("별점을 입력하세요. (1 ~ 5)");
									star = In.getInt("");
									if (1 <= star && star <= 5)
										break;
								}
								newStar.setStar(star);
								newStar.setId(Main.login.getId());
								newStar.setRecipe(viewNo);
								if ((Integer) Execute.run(new RecipeStarServiceImpl(), newStar) == 1) {
									Out.sysln("별점이 등록되었습니다.");
								} else {
									Out.sys("별점은 한 레시피에 아이디당 한 번만 등록할 수 있습니다.");
									Out.sysln("별점을 수정하고 싶으시면 취소 후 다시 등록해 주세요.");
								}
								break view;
							case "2":
								StarVO deStar = new StarVO();
								deStar.setId(Main.login.getId());
								deStar.setRecipe(viewNo);
								if ((Integer) Execute.run(new RecipeDestarServiceImpl(), deStar) == 1) {
									Out.sysln("별점이 취소되었습니다.");
								} else {
									Out.sysln("해당 레시피에 별점을 등록한 적이 없습니다.");
								}
								break view;
							case "3":
								ReplyVO newReply = new ReplyVO();
								String newReplyContent = "";
								while (true) {
									Out.sys("댓글을 입력하세요.");
									newReplyContent = In.getStr("");
									if (newReplyContent.length() != 0)
										break;
									else
										Out.sys("내용을 입력해 주세요.");
								}
								newReply.setContent(newReplyContent);
								newReply.setWriter(Main.login.getId());
								newReply.setRecipeNo(viewNo);
								if ((Integer) Execute.run(new RecipeStarServiceImpl(), newReply) == 1) {
									Out.sysln("댓글이 등록되었습니다.");
								} else {
									Out.sysln("댓글이 정상적으로 등록되지 않았습니다. 다시 시도해 주세요.");
								}
								break view;
							case "4":
								ReplyVO updateReply = new ReplyVO();
								updateReply.setNo(In.getLong("수정할 댓글번호"));
								updateReply.setWriter(Main.login.getId());
								if ((boolean) Execute.run(new RecipeCheckReplyWriterServiceImpl(), updateReply)) {
									updateReply.setContent(In.getStr("수정할 내용"));
									if ((Integer) Execute.run(new RecipeUpdateReplyServiceImpl(), updateReply) == 1) {
										Out.sysln("댓글이 수정되었습니다.");
									} else {
										Out.sysln("댓글이 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
									}
								} else {
									Out.sysln("본인이 작성한 댓글만 수정할 수 있습니다.");
								}
								break view;
							case "5":
								Long delRepNo = In.getLong("삭제할 댓글번호");
								if ((boolean) Execute.run(new RecipeCheckReplyWriterServiceImpl(), delRepNo)) {
									if ((Integer) Execute.run(new RecipeUpdateReplyServiceImpl(), delRepNo) == 1) {
										Out.sysln("댓글이 삭제되었습니다.");
									} else {
										Out.sysln("댓글이 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
									}
								} else {
									Out.sysln("본인이 작성한 댓글만 삭제할 수 있습니다.");
								}
								break view;
							case "0":
								Out.sysln("이전 메뉴로 돌아갑니다.");
								break view;
							default:
								Out.sysln("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
							}
						}
					}
					break;
				case "0":
					Out.sysln("이전 메뉴로 돌아갑니다.");
					return;
				case "4":
					if (Main.login != null) {
						Out.titleMini("레시피 등록", 30);
						RecipeVO writeVO = new RecipeVO();
						List<String> ingList = new ArrayList<>();
						writeVO.setTitle(In.getStr("제목"));
						writeVO.setContent(In.getStr("내용"));
						writeVO.setWriter(Main.login.getId());
						Out.sys("사용된 식재료를 모두 태그해 주세요. 모두 입력한 후 0을 입력해 주세요.");
						while (true) {
							String tag = In.getStr("");
							if (tag.equals("0")) {
								break;
							} else if (!Check.isAllKor(tag)) {
								Out.sys("한글로만 입력 가능합니다.");
							} else {
								ingList.add(tag);
							}
						}
						if ((Integer) Execute.run(new RecipeWriteServiceImpl(),
								new Object[] { writeVO, ingList }) == 1) {
							Out.sysln("레시피가 정상적으로 등록되었습니다.");
						} else
							Out.sysln("레시피가 정상적으로 등록되지 않았습니다. 다시 시도해 주세요.");
						break;
					}
				case "5":
					if (Main.login != null) {
						PrintRecipe
								.print((List<RecipeVO>) Execute.run(new RecipeMyListServiceImpl(), Main.login.getId()));

						break;
					}
				case "6":
					if (Main.login != null) {
						Out.titleMini("레시피 수정", 30);
						RecipeVO updateRecipe = new RecipeVO();
						Long updateNo = In.getLong("수정할 글번호");
						updateRecipe.setNo(updateNo);
						updateRecipe.setId(Main.login.getId());
						if ((boolean) Execute.run(new RecipeCheckWriterServiceImpl(), updateRecipe)) {
							updateRecipe = (RecipeVO) Execute.run(new RecipeViewServiceImpl(), updateNo);
							PrintRecipe.print(updateRecipe);
							update: while (true) {
								boolean updateIng = false;
								Out.sys("수정할 항목을 선택하세요.");
								Out.sys("1. 제목  2. 내용  3. 식재료  9. 취소  0. 수정완료");
								switch (In.getStr("")) {
								case "1":
									updateRecipe.setTitle(In.getStr("제목"));
									break update;
								case "2":
									updateRecipe.setContent(In.getStr("내용"));
									break update;
								case "3":
									updateIng = true;
									Out.sys("사용된 식재료를 모두 태그해 주세요. 모두 입력한 후 0을 입력해 주세요.");
									while (true) {
										String updateTag = In.getStr("");
										if (updateTag.equals("0")) {
											break;
										} else if (!Check.isAllKor(updateTag)) {
											Out.sys("한글로만 입력 가능합니다.");
										} else {
											IngredientVO updIng = new IngredientVO();
											updIng.setName(updateTag);
											updateRecipe.getIngreList().add(updIng);
										}
									}
								case "9":
									Out.sysln("수정을 취소하고 이전으로 돌아갑니다.");
									break update;
								case "0":
									if ((Integer) Execute.run(new RecipeUpdateServiceImpl(),
											new Object[] { updateRecipe, updateIng }) == 1) {
										Out.sysln("레시피가 정상적으로 수정되었습니다.");
									} else
										Out.sysln("레시피가 정상적으로 수정되지 않았습니다. 다시 시도해 주세요.");
									break update;
								default:
									Out.sys("잘못 누르셨습니다. 메뉴번호를 확인해 주세요.");
								}
							}

						} else {
							Out.sysln("본인이 작성한 댓글만 수정할 수 있습니다.");
						}
						break;
					}
				case "7":
					if (Main.login != null) {
						Out.titleMini("레시피 삭제", 30);
						Long deleteNo = In.getLong("삭제할 글번호");
						RecipeVO deleteVO = (RecipeVO) Execute.run(new RecipeViewServiceImpl(), deleteNo);
						if (deleteVO == null) {
							Out.sysln("존재하지 않는 글번호입니다.");
							break;
						} else if (!(deleteVO.getId().equals(Main.login.getId()))) {
							Out.sysln("본인이 작성한 레시피만 삭제할 수 있습니다.");
							break;
						} else {
							if ((Integer) Execute.run(new RecipeDeleteServiceImpl(), deleteNo) == 1) {
								Out.sysln("레시피가 정상적으로 삭제되었습니다.");
							} else
								Out.sysln("레시피가 정상적으로 삭제되지 않았습니다. 다시 시도해 주세요.");
							break;
						}
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
