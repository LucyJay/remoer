package com.remoer.recipe.vo;

public class ReplyVO {

	private Long no;
	private String content, writer, write_date;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	@Override
	public String toString() {
		return "ReplyVO [no=" + no + ", content=" + content + ", writer=" + writer + ", write_date=" + write_date + "]";
	}

}
