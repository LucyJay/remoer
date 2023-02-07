package com.remoer.qna.vo;

public class FaqVO {

	private Long no;
	private String title, content, answer;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "FaqVO [no=" + no +", title=" + title + ", content=" + content + ", answer="
				+ answer + "]";
	}

}
