package com.remoer.recipe.vo;

import java.util.List;

import com.remoer.ingredient.vo.IngredientVO;

public class RecipeVO {
	
	private Long no, cntStar, cntReply;
	private String title, content, id, writer, write_date, update_date;
	private List<IngredientVO> ingreList;
	private List<ReplyVO> replyList;
	private Double avgStar;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getCntStar() {
		return cntStar;
	}
	public void setCntStar(Long cntStar) {
		this.cntStar = cntStar;
	}
	public Long getCntReply() {
		return cntReply;
	}
	public void setCntReply(Long cntReply) {
		this.cntReply = cntReply;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public List<IngredientVO> getIngreList() {
		return ingreList;
	}
	public void setIngreList(List<IngredientVO> ingreList) {
		this.ingreList = ingreList;
	}
	public List<ReplyVO> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<ReplyVO> replyList) {
		this.replyList = replyList;
	}
	public Double getAvgStar() {
		return avgStar;
	}
	public void setAvgStar(Double avgStar) {
		this.avgStar = avgStar;
	}
	@Override
	public String toString() {
		return "RecipeVO [no=" + no + ", cntStar=" + cntStar + ", cntReply=" + cntReply + ", title=" + title
				+ ", content=" + content + ", id=" + id + ", writer=" + writer + ", write_date=" + write_date
				+ ", update_date=" + update_date + ", ingreList=" + ingreList + ", replyList=" + replyList
				+ ", avgStar=" + avgStar + "]";
	}
	
	
	

}
