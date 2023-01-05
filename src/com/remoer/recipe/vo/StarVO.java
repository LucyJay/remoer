package com.remoer.recipe.vo;

public class StarVO {
	
	private int star;
	private String id;
	private Long recipe;
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getRecipe() {
		return recipe;
	}
	public void setRecipe(Long recipe) {
		this.recipe = recipe;
	}
	@Override
	public String toString() {
		return "StarVO [star=" + star + ", id=" + id + ", recipe=" + recipe + "]";
	}
	
	

}
