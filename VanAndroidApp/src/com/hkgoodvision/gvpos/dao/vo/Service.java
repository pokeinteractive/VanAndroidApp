package com.hkgoodvision.gvpos.dao.vo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 帖子实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Service extends Entity {

	String orderId;
	String remark;
	String custPhone;
	String orderDate;
	String timeslot;
	String fromLocation;
	String toLocation;
	String price;
		

	public static Service parse(InputStream inputStream) throws Exception {
		Service service = new Service();

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONObject oneObject = jObject.getJSONObject("order");

		// get the List Array
		int i = 0;

		//JSONObject oneObject = jArray.getJSONObject(0);
		// Pulling items from the array
		String order_id = oneObject.getString("order_id");
		String remark = oneObject.getString("remark");
		String cust_phone = oneObject.getString("cust_phone");
		String order_date = oneObject.getString("order_date");
		String timeslot = oneObject.getString("timeslot");
		String from_location = oneObject.getString("from_location");
		String to_location = oneObject.getString("to_locaiton");
		//String price = oneObject.getString("price");

		service.setOrderId(order_id);
		service.setRemark(remark);
		service.setCustPhone(cust_phone);
		service.setOrderDate(order_date);
		service.setTimeslot(timeslot);
		service.setFromLocation(from_location);
		service.setToLocation(to_location);
		//service.setPrice(price);

		
		System.out.println(order_id + ":" + cust_phone + ":" + timeslot);

		
		return service;

	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getCustPhone() {
		return custPhone;
	}



	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}



	public String getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}



	public String getTimeslot() {
		return timeslot;
	}



	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}



	public String getFromLocation() {
		return fromLocation;
	}



	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}



	public String getToLocation() {
		return toLocation;
	}



	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}

	

}
