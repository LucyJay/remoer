package com.remoer.order.vo;

import java.util.List;

import com.remoer.ingredient.vo.GoodsVO;

public class OrderVO {
	private Long no;
	private String id, name, address, tel, status, order_date;
	private String dlv_date = "-";
	private Integer totalPrice;
	private List<GoodsVO> list;

	public Integer totalPrice() {
		Integer total_price = 0;
		if (list != null && list.size() > 0) {
			for (GoodsVO vo : list) {
				total_price += vo.totalPrice();
			}
		}
		return total_price;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getDlv_date() {
		return dlv_date;
	}

	public void setDlv_date(String dlv_date) {
		this.dlv_date = dlv_date;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<GoodsVO> getList() {
		return list;
	}

	public void setList(List<GoodsVO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "OrderVO [no=" + no + ", id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel
				+ ", status=" + status + ", order_date=" + order_date + ", dlv_date=" + dlv_date + ", totalPrice="
				+ totalPrice + ", list=" + list + "]";
	}

}
