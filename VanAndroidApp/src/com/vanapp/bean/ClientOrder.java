package com.vanapp.bean;

import java.sql.Timestamp;

public class ClientOrder {
	
	int _id;
	String message;
	String phone;
	String orderId;
	Timestamp orderTime;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	
	public final static String ORDER_ID = "orderId";
	public final static String MESSAGE = "message";
	public final static String PHONE = "phone";
	public final static String ORDERTIME = "orderTime";

	
}
