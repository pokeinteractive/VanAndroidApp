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
public class SubjectList extends Entity {

	public final static int CATALOG_ASK = 1;
	public final static int CATALOG_SHARE = 2;
	public final static int CATALOG_OTHER = 3;
	public final static int CATALOG_JOB = 4;
	public final static int CATALOG_SITE = 5;

	private int pageSize = 20;
	private int count;
	private List<Subject> subjectList = new ArrayList<Subject>();

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

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public static SubjectList parse(InputStream inputStream) throws IOException, JSONException {
		SubjectList subjectList = new SubjectList();

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		if (jObject.isNull("subject")) {
			return subjectList;
		}

		JSONArray jArray = jObject.getJSONArray("subject");

		// get the List Array
		int i = 0;
		for (i = 0; i < jArray.length(); i++) {
			JSONObject oneObject = jArray.getJSONObject(i);
			// Pulling items from the array
			String subjectId = oneObject.getString("subject_id");
			String title = oneObject.getString("subject");

			System.out.println(subjectId + ":" + title);

			Subject subject = new Subject();

			subject.setSubjectId(Integer.parseInt(subjectId));
			subject.setTitle(title);
			subject.setPhoto("s" + subjectId + ".jpg");

			subjectList.getSubjectList().add(subject);
		}

		subjectList.setCount(i);

		return subjectList;

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
