package com.remoer.ingredient.vo;

public class GoodsVO {

	private String goods_name;
	private Integer quantity, price;

	public Integer totalPrice() {
		return quantity * price;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "GoodsVO [goods_name=" + goods_name + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
