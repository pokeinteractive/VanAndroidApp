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

/**
 * 帖子列表实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class MembershipList extends Entity {

	private int pageSize = 20;
	private int count;
	private List<EarnPoint> earnPointList = new ArrayList<EarnPoint>();

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

	public List<EarnPoint> getEarnPointList() {
		return earnPointList;
	}

	public void setEarnPointList(List<EarnPoint> serviceList) {
		this.earnPointList = serviceList;
	}

	public static MembershipList parse(InputStream inputStream) throws IOException, JSONException {
		MembershipList memberShipList = new MembershipList();
		EarnPoint earnPoint = new EarnPoint();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONArray jArray = null;
		if (jObject.isNull("earnpoint")) {
			return memberShipList;
		}
		jArray = jObject.getJSONArray("earnpoint");

		// get the List Array
		int i = 0;
		for (i = 0; i < jArray.length(); i++) {
			JSONObject oneObject = jArray.getJSONObject(i);
			
			if (oneObject == null)
				continue;
			
			// Pulling items from the array
			String service_id = oneObject.getString("service_id");
			String service_name = oneObject.getString("service_name");
			String point = oneObject.getString("point");
			String photo = oneObject.getString("photo");
			String address = oneObject.getString("address");

			System.out.println(service_id + ":" + service_name + ":" + point + "|" + photo);

			earnPoint = new EarnPoint();

			earnPoint.id = Integer.parseInt(service_id);
			earnPoint.setServiceId(Integer.parseInt(service_id));
			earnPoint.setServiceName(service_name);
			earnPoint.setPoint(Integer.parseInt(point));
			earnPoint.setPhoto(photo);
			earnPoint.setAddress(address);

			memberShipList.getEarnPointList().add(earnPoint);
		}

		memberShipList.setCount(i);

		return memberShipList;

	}
}
