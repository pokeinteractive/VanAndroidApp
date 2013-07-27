package com.hkgoodvision.gvpos.constant;

import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.hkgoodvision.gvpos.common.StringUtils;

/**
 * 接口URL实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class URLs implements Serializable {
	
	public final static String HOST = "van.poke.com.hk";//192.168.1.213  www.oschina.net
	
	//public final static String WEDDINGIDOHOST = "www.weddingido.com";//192.168.1.213  www.oschina.net
	public final static String WEDDINGIDOHOST = "van.poke.com.hk";//  www.oschina.net
	
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	private final static String URL_SPLITTER = "/";
	private final static String URL_UNDERLINE = "_";
	
	//private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	
	private final static String URL_WEDDINGIDO_API_HOST = HTTP + WEDDINGIDOHOST + URL_SPLITTER;
	
	
	// Weddingido URL
	public final static String SUBJECT_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getSubjectList";
	public final static String SERVICE_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getServiceList";
	public final static String SERVICE_DETAIL = URL_WEDDINGIDO_API_HOST+"jsonapi/getService";
	public final static String FULL_COMMENT_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getCommentList/0";
	public final static String SERVICE_COMMENT_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getCommentList";
	
	
	public final static String REWARD_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getRewardList";
	// post data
	public final static String REWARD_APPLY = URL_WEDDINGIDO_API_HOST+"jsonapi/rewardapply";
	public final static String REGISTER = URL_WEDDINGIDO_API_HOST+"jsonapi/register";
	public final static String EARN_POINT = URL_WEDDINGIDO_API_HOST+"jsonapi/earnpoint";
	public final static String AUDIO_CHECK = URL_WEDDINGIDO_API_HOST+"jsonapi/audioCheck";
	public final static String MEMBERSHIP_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getEarnPointList";
	
	
	
	public final static String IMAGE_PATH_URL = URL_WEDDINGIDO_API_HOST+"upload/";
	public final static String SUBJECT_IMAGE_PATH_URL = URL_WEDDINGIDO_API_HOST+"graphics/subject/";
	
	
	
	private final static String URL_HOST = "oschina.net";
	private final static String URL_WWW_HOST = "www."+URL_HOST;
	private final static String URL_MY_HOST = "my."+URL_HOST;
	
	private final static String URL_TYPE_NEWS = URL_WWW_HOST + URL_SPLITTER + "news" + URL_SPLITTER;
	private final static String URL_TYPE_SOFTWARE = URL_WWW_HOST + URL_SPLITTER + "p" + URL_SPLITTER;
	private final static String URL_TYPE_QUESTION = URL_WWW_HOST + URL_SPLITTER + "question" + URL_SPLITTER;
	private final static String URL_TYPE_BLOG = URL_SPLITTER + "blog" + URL_SPLITTER;
	private final static String URL_TYPE_TWEET = URL_SPLITTER + "tweet" + URL_SPLITTER;
	private final static String URL_TYPE_ZONE = URL_MY_HOST + URL_SPLITTER + "u" + URL_SPLITTER;
	private final static String URL_TYPE_QUESTION_TAG = URL_TYPE_QUESTION + "tag" + URL_SPLITTER;
	
	public final static int URL_OBJ_TYPE_OTHER = 0x000;
	public final static int URL_OBJ_TYPE_NEWS = 0x001;
	public final static int URL_OBJ_TYPE_SOFTWARE = 0x002;
	public final static int URL_OBJ_TYPE_QUESTION = 0x003;
	public final static int URL_OBJ_TYPE_ZONE = 0x004;
	public final static int URL_OBJ_TYPE_BLOG = 0x005;
	public final static int URL_OBJ_TYPE_TWEET = 0x006;
	public final static int URL_OBJ_TYPE_QUESTION_TAG = 0x007;
	
	private int objId;
	private String objKey = "";
	private int objType;
	
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public String getObjKey() {
		return objKey;
	}
	public void setObjKey(String objKey) {
		this.objKey = objKey;
	}
	public int getObjType() {
		return objType;
	}
	public void setObjType(int objType) {
		this.objType = objType;
	}
	
	/**
	 * 转化URL为URLs实体
	 * @param path
	 * @return 不能转化的链接返回null
	 */
	public final static URLs parseURL(String path) {
		if(StringUtils.isEmpty(path))return null;
		path = formatURL(path);
		URLs urls = null;
		String objId = "";
		try {
			URL url = new URL(path);
			//站内链接
			if(url.getHost().contains(URL_HOST)){
				urls = new URLs();
				//www
				if(path.contains(URL_WWW_HOST )){
					//新闻  www.oschina.net/news/27259/mobile-internet-market-is-small
					if(path.contains(URL_TYPE_NEWS)){
						objId = parseObjId(path, URL_TYPE_NEWS);
						urls.setObjId(StringUtils.toInt(objId));
						urls.setObjType(URL_OBJ_TYPE_NEWS);
					}
					//软件  www.oschina.net/p/jx
					else if(path.contains(URL_TYPE_SOFTWARE)){
						urls.setObjKey(parseObjKey(path, URL_TYPE_SOFTWARE));
						urls.setObjType(URL_OBJ_TYPE_SOFTWARE);
					}
					//问答
					else if(path.contains(URL_TYPE_QUESTION)){
						//问答-标签  http://www.oschina.net/question/tag/python
						if(path.contains(URL_TYPE_QUESTION_TAG)){
							urls.setObjKey(parseObjKey(path, URL_TYPE_QUESTION_TAG));
							urls.setObjType(URL_OBJ_TYPE_QUESTION_TAG);
						}
						//问答  www.oschina.net/question/12_45738
						else{
							objId = parseObjId(path, URL_TYPE_QUESTION);
							String[] _tmp = objId.split(URL_UNDERLINE);
							urls.setObjId(StringUtils.toInt(_tmp[1]));
							urls.setObjType(URL_OBJ_TYPE_QUESTION);
						}
					}
					//other
					else{
						urls.setObjKey(path);
						urls.setObjType(URL_OBJ_TYPE_OTHER);
					}
				}
				//my
				else if(path.contains(URL_MY_HOST)){					
					//博客  my.oschina.net/szpengvictor/blog/50879
					if(path.contains(URL_TYPE_BLOG)){
						objId = parseObjId(path, URL_TYPE_BLOG);
						urls.setObjId(StringUtils.toInt(objId));
						urls.setObjType(URL_OBJ_TYPE_BLOG);
					}
					//动弹  my.oschina.net/dong706/tweet/612947
					else if(path.contains(URL_TYPE_TWEET)){
						objId = parseObjId(path, URL_TYPE_TWEET);
						urls.setObjId(StringUtils.toInt(objId));
						urls.setObjType(URL_OBJ_TYPE_TWEET);
					}
					//个人专页  my.oschina.net/u/12
					else if(path.contains(URL_TYPE_ZONE)){
						objId = parseObjId(path, URL_TYPE_ZONE);
						urls.setObjId(StringUtils.toInt(objId));
						urls.setObjType(URL_OBJ_TYPE_ZONE);
					}
					else{
						//另一种个人专页  my.oschina.net/dong706
						int p = path.indexOf(URL_MY_HOST+URL_SPLITTER) + (URL_MY_HOST+URL_SPLITTER).length();
						String str = path.substring(p);
						if(!str.contains(URL_SPLITTER)){
							urls.setObjKey(str);
							urls.setObjType(URL_OBJ_TYPE_ZONE);
						}
						//other
						else{
							urls.setObjKey(path);
							urls.setObjType(URL_OBJ_TYPE_OTHER);
						}
					}
				}
				//other
				else{
					urls.setObjKey(path);
					urls.setObjType(URL_OBJ_TYPE_OTHER);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			urls = null;
		}
		return urls;
	}

	/**
	 * 解析url获得objId
	 * @param path
	 * @param url_type
	 * @return
	 */
	private final static String parseObjId(String path, String url_type){
		String objId = "";
		int p = 0;
		String str = "";
		String[] tmp = null;
		p = path.indexOf(url_type) + url_type.length();
		str = path.substring(p);
		if(str.contains(URL_SPLITTER)){
			tmp = str.split(URL_SPLITTER);
			objId = tmp[0];
		}else{
			objId = str;
		}
		return objId;
	}
	
	/**
	 * 解析url获得objKey
	 * @param path
	 * @param url_type
	 * @return
	 */
	private final static String parseObjKey(String path, String url_type){
		path = URLDecoder.decode(path);
		String objKey = "";
		int p = 0;
		String str = "";
		String[] tmp = null;
		p = path.indexOf(url_type) + url_type.length();
		str = path.substring(p);
		if(str.contains("?")){
			tmp = str.split("?");
			objKey = tmp[0];
		}else{
			objKey = str;
		}
		return objKey;
	}
	
	/**
	 * 对URL进行格式处理
	 * @param path
	 * @return
	 */
	private final static String formatURL(String path) {
		if(path.startsWith("http://") || path.startsWith("https://"))
			return path;
		return "http://" + URLEncoder.encode(path);
	}	
}