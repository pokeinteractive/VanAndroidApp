package com.hkgoodvision.gvpos.dao.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 帖子列表实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ServiceList extends Entity {

	private int pageSize = 20;
	private int count;
	private List<Service> serviceList = new ArrayList<Service>();

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public static ServiceList parse(InputStream inputStream) throws IOException, JSONException {
		ServiceList serviceList = new ServiceList();
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONArray jArray = null;
		if (jObject.isNull("order")) {
			return serviceList;
		}
		jArray = jObject.getJSONArray("order");
		
		
//		int totalRecord = jObject.getInt("total");
//		serviceList.setCount(totalRecord);
		

		// get the List Array
		int i = 0;
		for (i = 0; i < jArray.length(); i++) {
			JSONObject oneObject = jArray.getJSONObject(i);
			
			if (oneObject == null)
				continue;
			
			String order_id = oneObject.getString("order_id");
			String remark = oneObject.getString("remark");
			String cust_phone = oneObject.getString("cust_phone");
			String order_date = oneObject.getString("order_date");
			String timeslot = oneObject.getString("timeslot");
			String from_location = oneObject.getString("from_location");
			String to_location = oneObject.getString("to_locaiton");
			String price = oneObject.getString("price");

			Service service = new Service();
			service.setOrderId(order_id);
			service.setRemark(remark);
			service.setCustPhone(cust_phone);
			service.setOrderDate(order_date);
			service.setTimeslot(timeslot);
			service.setFromLocation(from_location);
			service.setToLocation(to_location);
			service.setPrice(price);

			serviceList.getServiceList().add(service);
		}

		serviceList.setCount(i);

		return serviceList;

	}
}
