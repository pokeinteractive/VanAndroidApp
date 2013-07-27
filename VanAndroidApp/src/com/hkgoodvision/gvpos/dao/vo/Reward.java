package com.hkgoodvision.gvpos.dao.vo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 帖子实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Reward extends Entity {

	// public final static int CATALOG_ASK = 1;
	// public final static int CATALOG_SHARE = 2;
	// public final static int CATALOG_OTHER = 3;
	// public final static int CATALOG_JOB = 4;
	// public final static int CATALOG_SITE = 5;

	private String photo;
	private int rewardId;
	private String title;
	private int point;

	public int getPoint() {
		return point;
	}


	public void setPoint(int point) {
		this.point = point;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public int getRewardId() {
		return rewardId;
	}


	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


//	public static EarnPoint parse(InputStream inputStream) throws Exception {
//		EarnPoint service = new EarnPoint();
//
//		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//		StringBuilder responseStrBuilder = new StringBuilder();
//
//		String inputStr;
//		while ((inputStr = streamReader.readLine()) != null)
//			responseStrBuilder.append(inputStr);
//
//		JSONObject jObject = new JSONObject(responseStrBuilder.toString());
//
//		JSONArray jArray = jObject.getJSONArray("service");
//
//		// get the List Array
//		int i = 0;
//
//		JSONObject oneObject = jArray.getJSONObject(0);
//		// Pulling items from the array
//		String service_id = oneObject.getString("service_id");
//		String service_name = oneObject.getString("service_name");
//		String member_id = oneObject.getString("member_id");
//		String member_name = oneObject.getString("member_name");
//		String subject_id = oneObject.getString("subject_id");
//		String created = oneObject.getString("created");
//		String website = oneObject.getString("website");
//		String address = oneObject.getString("address");
//		String phone = oneObject.getString("phone");
//		String subject = oneObject.getString("subject");
//
//		service.id = Integer.parseInt(service_id);
//		service.setSubjectId(Integer.parseInt(subject_id));
//		service.setServiceId(Integer.parseInt(service_id));
//		service.setServiceName(service_name);
//		service.setMemberId(Integer.parseInt(member_id));
//		service.setMemberName(member_name);
//
//		service.setCreated(created);
//		service.setWebsite(website);
//		service.setPhone(phone);
//		service.setAddress(address);
//		service.setSubject(subject);
//
//		service.setContactPerson(oneObject.getString("contact_person"));
//		service.setDescription(oneObject.getString("description"));
//		service.setPriceDesc(oneObject.getString("price_desc"));
//		service.setPromoDesc(oneObject.getString("promo_desc"));
//		service.setGood(Integer.parseInt(oneObject.getString("good")));
//		service.setFair(Integer.parseInt(oneObject.getString("fair")));
//		service.setBad(Integer.parseInt(oneObject.getString("bad")));
//		service.setMainPoint(oneObject.getString("main_point"));
//		service.setLocationId(oneObject.getString("location_id"));
//		if (!oneObject.isNull("map_lat")) {
//			service.setMapLat(Double.parseDouble(oneObject.getString("map_lat")));
//		}
//		if (!oneObject.isNull("map_long")) {
//			service.setMapLong(Double.parseDouble(oneObject.getString("map_long")));
//		}
//		System.out.println(service_id + ":" + service_name + ":" + member_name);
//
//		oneObject = jArray.getJSONObject(1);
//		String photo = "";
//		try {
//			photo = oneObject.getString("photo");
//			service.setCompanyEng(oneObject.getString("company_name_eng"));
//			service.setCompanyChi(oneObject.getString("company_name_chi"));
//		} catch (Exception e) {
//
//		}
//		service.setPhoto(photo);
//
//		return service;
//
//	}

}
