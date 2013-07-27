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
public class Service extends Entity {



	private int subjectId;
	private int serviceId;
	private int point;
	private int memberId;
	private String serviceName;
	private String memberName;
	private String photo;
	private String created;
	private int commentCount;

	// detail field
	private String subject;
	private String website;
	private String phone;
	private String address;
	private int favorite;
	private String description;
	private String priceDesc;
	private String promoDesc;
	private int good;
	private int fair;
	private int bad;
	private String contactPerson;
	private String mainPoint;
	private String locationId;
	private double mapLat;
	private double mapLong;

	private String companyEng;
	private String companyChi;

	public String getCompanyEng() {
		return companyEng;
	}

	public void setCompanyEng(String companyEng) {
		this.companyEng = companyEng;
	}

	public String getCompanyChi() {
		return companyChi;
	}

	public void setCompanyChi(String companyChi) {
		this.companyChi = companyChi;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceDesc() {
		return priceDesc;
	}

	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}

	public String getPromoDesc() {
		return promoDesc;
	}

	public void setPromoDesc(String promoDesc) {
		this.promoDesc = promoDesc;
	}

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getFair() {
		return fair;
	}

	public void setFair(int fair) {
		this.fair = fair;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getMainPoint() {
		return mainPoint;
	}

	public void setMainPoint(String mainPoint) {
		this.mainPoint = mainPoint;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public double getMapLat() {
		return mapLat;
	}

	public void setMapLat(double mapLat) {
		this.mapLat = mapLat;
	}

	public double getMapLong() {
		return mapLong;
	}

	public void setMapLong(double mapLong) {
		this.mapLong = mapLong;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public static Service parse(InputStream inputStream) throws Exception {
		Service service = new Service();

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONArray jArray = jObject.getJSONArray("service");

		// get the List Array
		int i = 0;

		JSONObject oneObject = jArray.getJSONObject(0);
		// Pulling items from the array
		String service_id = oneObject.getString("service_id");
		String service_name = oneObject.getString("service_name");
		String member_id = oneObject.getString("member_id");
		String member_name = oneObject.getString("member_name");
		String subject_id = oneObject.getString("subject_id");
		String created = oneObject.getString("created");
		String website = oneObject.getString("website");
		String address = oneObject.getString("address");
		String phone = oneObject.getString("phone");
		String subject = oneObject.getString("subject");

		service.id = Integer.parseInt(service_id);
		service.setSubjectId(Integer.parseInt(subject_id));
		service.setServiceId(Integer.parseInt(service_id));
		service.setServiceName(service_name);
		service.setMemberId(Integer.parseInt(member_id));
		service.setMemberName(member_name);

		service.setCreated(created);
		service.setWebsite(website);
		service.setPhone(phone);
		service.setAddress(address);
		service.setSubject(subject);

		service.setContactPerson(oneObject.getString("contact_person"));
		service.setDescription(oneObject.getString("description"));
		service.setPriceDesc(oneObject.getString("price_desc"));
		service.setPromoDesc(oneObject.getString("promo_desc"));
		service.setGood(Integer.parseInt(oneObject.getString("good")));
		service.setFair(Integer.parseInt(oneObject.getString("fair")));
		service.setBad(Integer.parseInt(oneObject.getString("bad")));
		service.setMainPoint(oneObject.getString("main_point"));
		service.setLocationId(oneObject.getString("location_id"));
		if (!oneObject.isNull("map_lat")) {
			service.setMapLat(Double.parseDouble(oneObject.getString("map_lat")));
		}
		if (!oneObject.isNull("map_long")) {
			service.setMapLong(Double.parseDouble(oneObject.getString("map_long")));
		}
		System.out.println(service_id + ":" + service_name + ":" + member_name);

		oneObject = jArray.getJSONObject(1);
		String photo = "";
		try {
			photo = oneObject.getString("photo");
			service.setCompanyEng(oneObject.getString("company_name_eng"));
			service.setCompanyChi(oneObject.getString("company_name_chi"));
		} catch (Exception e) {

		}
		service.setPhoto(photo);

		return service;

	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
