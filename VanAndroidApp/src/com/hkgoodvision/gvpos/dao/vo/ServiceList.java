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
		Service service = new Service();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONArray jArray = null;
		if (jObject.isNull("service")) {
			return serviceList;
		}
		jArray = jObject.getJSONArray("service");
		
		
//		int totalRecord = jObject.getInt("total");
//		serviceList.setCount(totalRecord);
		

		// get the List Array
		int i = 0;
		for (i = 0; i < jArray.length(); i++) {
			JSONObject oneObject = jArray.getJSONObject(i);
			
			if (oneObject == null)
				continue;
			
			// Pulling items from the array
			String service_id = oneObject.getString("service_id");
			String service_name = oneObject.getString("service_name");
			String member_id = oneObject.getString("member_id");
			String member_name = oneObject.getString("member_name");
			String point = oneObject.getString("point");
			String subject_id = oneObject.getString("subject_id");
			String photo = oneObject.getString("photo");
			String created = oneObject.getString("created");
			String commentCount = oneObject.getString("comment");

			Log.d("ServiceList", service_id + ":" + service_name + ":" + member_name);

			service = new Service();

			service.id = Integer.parseInt(service_id);
			service.setSubjectId(Integer.parseInt(subject_id));
			service.setServiceId(Integer.parseInt(service_id));
			service.setPoint(Integer.parseInt(point));
			service.setServiceName(service_name);
			service.setMemberId(Integer.parseInt(member_id));
			service.setMemberName(member_name);
			service.setPhoto(photo);
			service.setCommentCount(Integer.parseInt(commentCount));
			service.setCreated(created);

			serviceList.getServiceList().add(service);
		}

		serviceList.setCount(i);

		return serviceList;

	}
	//
	// public static SubjectList parse(InputStream inputStream) throws
	// IOException, AppException {
	// SubjectList postlist = new SubjectList();
	// Post post = null;
	// // 获得XmlPullParser解析器
	// XmlPullParser xmlParser = Xml.newPullParser();
	// try {
	// xmlParser.setInput(inputStream, UTF8);
	// // 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
	// int evtType = xmlParser.getEventType();
	// // 一直循环，直到文档结束
	// while (evtType != XmlPullParser.END_DOCUMENT) {
	// String tag = xmlParser.getName();
	// switch (evtType) {
	// case XmlPullParser.START_TAG:
	// if (tag.equalsIgnoreCase("postCount")) {
	// postlist.postCount = StringUtils.toInt(xmlParser.nextText(), 0);
	// } else if (tag.equalsIgnoreCase("pageSize")) {
	// postlist.pageSize = StringUtils.toInt(xmlParser.nextText(), 0);
	// } else if (tag.equalsIgnoreCase(Post.NODE_START)) {
	// post = new Post();
	// } else if (post != null) {
	// if (tag.equalsIgnoreCase(Post.NODE_ID)) {
	// post.id = StringUtils.toInt(xmlParser.nextText(), 0);
	// } else if (tag.equalsIgnoreCase(Post.NODE_TITLE)) {
	// post.setTitle(xmlParser.nextText());
	// } else if (tag.equalsIgnoreCase(Post.NODE_FACE)) {
	// post.setFace(xmlParser.nextText());
	// } else if (tag.equalsIgnoreCase(Post.NODE_AUTHOR)) {
	// post.setAuthor(xmlParser.nextText());
	// } else if (tag.equalsIgnoreCase(Post.NODE_AUTHORID)) {
	// post.setAuthorId(StringUtils.toInt(xmlParser.nextText(), 0));
	// } else if (tag.equalsIgnoreCase(Post.NODE_ANSWERCOUNT)) {
	// post.setAnswerCount(StringUtils.toInt(xmlParser.nextText(), 0));
	// } else if (tag.equalsIgnoreCase(Post.NODE_VIEWCOUNT)) {
	// post.setViewCount(StringUtils.toInt(xmlParser.nextText(), 0));
	// } else if (tag.equalsIgnoreCase(Post.NODE_PUBDATE)) {
	// post.setPubDate(xmlParser.nextText());
	// }
	// }
	// // 通知信息
	// else if (tag.equalsIgnoreCase("notice")) {
	// postlist.setNotice(new Notice());
	// } else if (postlist.getNotice() != null) {
	// if (tag.equalsIgnoreCase("atmeCount")) {
	// postlist.getNotice().setAtmeCount(StringUtils.toInt(xmlParser.nextText(),
	// 0));
	// } else if (tag.equalsIgnoreCase("msgCount")) {
	// postlist.getNotice().setMsgCount(StringUtils.toInt(xmlParser.nextText(),
	// 0));
	// } else if (tag.equalsIgnoreCase("reviewCount")) {
	// postlist.getNotice().setReviewCount(StringUtils.toInt(xmlParser.nextText(),
	// 0));
	// } else if (tag.equalsIgnoreCase("newFansCount")) {
	// postlist.getNotice().setNewFansCount(StringUtils.toInt(xmlParser.nextText(),
	// 0));
	// }
	// }
	// break;
	// case XmlPullParser.END_TAG:
	// // 如果遇到标签结束，则把对象添加进集合中
	// if (tag.equalsIgnoreCase("post") && post != null) {
	// postlist.getPostlist().add(post);
	// post = null;
	// }
	// break;
	// }
	// // 如果xml没有结束，则导航到下一个节点
	// evtType = xmlParser.next();
	// }
	// } catch (XmlPullParserException e) {
	// throw AppException.xml(e);
	// } finally {
	// inputStream.close();
	// }
	// return postlist;
	// }
}
