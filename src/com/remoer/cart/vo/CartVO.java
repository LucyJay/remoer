package com.remoer.cart.vo;

public class CartVO {

	private Long no, goods_no;
	private String id, goods_name;
	private Integer price, quantity;

	public Integer totalPrice() {
		return quantity * price;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Long getGoods_no() {
		return goods_no;
	}

	public void setGoods_no(Long goods_no) {
		this.goods_no = goods_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartVO [no=" + no + ", goods_no=" + goods_no + ", id=" + id + ", goods_name=" + goods_name + ", price="
				+ price + ", quantity=" + quantity + "]";
	}

}
