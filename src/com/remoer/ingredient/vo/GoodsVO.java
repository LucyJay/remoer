package com.remoer.ingredient.vo;

public class GoodsVO {

	private String goods_name;
	private Long quantity, price;

	public Long totalPrice() {
		return quantity * price;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "GoodsVO [goods_name=" + goods_name + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
