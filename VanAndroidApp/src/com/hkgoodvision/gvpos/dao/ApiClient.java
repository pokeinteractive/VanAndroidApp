package com.hkgoodvision.gvpos.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppException;
import com.hkgoodvision.gvpos.constant.URLs;
import com.hkgoodvision.gvpos.dao.vo.MembershipList;
import com.hkgoodvision.gvpos.dao.vo.Result;
import com.hkgoodvision.gvpos.dao.vo.RewardList;
import com.hkgoodvision.gvpos.dao.vo.Service;
import com.hkgoodvision.gvpos.dao.vo.ServiceList;
import com.hkgoodvision.gvpos.dao.vo.SubjectList;

/**
 * API客户端接口：用于访问网络数据
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ApiClient {

	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";
	
	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 3;

	private static String appCookie;
	private static String appUserAgent;

	public static void cleanCookie() {
		appCookie = "";
	}
	
	private static String getCookie(AppContext appContext) {
		if(appCookie == null || appCookie == "") {
			appCookie = appContext.getProperty("cookie");
		}
		return appCookie;
	}
	
	private static String getUserAgent(AppContext appContext) {
		if(appUserAgent == null || appUserAgent == "") {
			StringBuilder ua = new StringBuilder("OSChina.NET");
			ua.append('/'+appContext.getPackageInfo().versionName+'_'+appContext.getPackageInfo().versionCode);//App版本
			ua.append("/Android");//手机系统平台
			ua.append("/"+android.os.Build.VERSION.RELEASE);//手机系统版本
			ua.append("/"+android.os.Build.MODEL); //手机型号
			ua.append("/"+appContext.getAppId());//客户端唯一标识
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}
	
	private static HttpClient getHttpClient() {        
        HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        // 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}	
	
	private static GetMethod getHttpGet(String url, String cookie, String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", URLs.HOST);
		httpGet.setRequestHeader("Connection","Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}
	
	private static PostMethod getHttpPost(String url, String cookie, String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", URLs.HOST);
		httpPost.setRequestHeader("Connection","Keep-Alive");
		httpPost.setRequestHeader("Cookie", cookie);
		httpPost.setRequestHeader("User-Agent", userAgent);
		return httpPost;
	}
	
	private static String _MakeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
			//不做URLEncoder处理
			//url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
		}

		return url.toString().replace("?&", "?");
	}
	
	/**
	 * get请求URL
	 * @param url
	 * @throws AppException 
	 */
	private static InputStream http_get(AppContext appContext, String url) throws AppException {	
		System.out.println("get_url==> "+url);
		String cookie = getCookie(appContext);
		String userAgent = getUserAgent(appContext);
		
		
		
		//new RetreiveFeedTask().execute(newUrl);
		
		
		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, cookie, userAgent);			
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
				responseBody = httpGet.getResponseBodyAsString();
				//System.out.println("XMLDATA=====>"+responseBody);
				break;				
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		
		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
		if(responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")){
			try {
				Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));	
				if(res.getErrorCode() == 0){
					appContext.Logout();
					appContext.getUnLoginHandler().sendEmptyMessage(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return new ByteArrayInputStream(responseBody.getBytes());
	}
	
	/**
	 * 公用post方法
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	private static InputStream _post(AppContext appContext, String url, Map<String, Object> params, Map<String,File> files) throws AppException {
		System.out.println("post_url==> "+url);
		String cookie = getCookie(appContext);
		String userAgent = getUserAgent(appContext);
		
		String responseBody = doPost(appContext, url, params, files, cookie, userAgent);
        
        responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
		if(responseBody.contains("result") && responseBody.contains("errorCode") && appContext.containsProperty("user.uid")){
			try {
				Result res = Result.parse(new ByteArrayInputStream(responseBody.getBytes()));	
				if(res.getErrorCode() == 0){
					appContext.Logout();
					appContext.getUnLoginHandler().sendEmptyMessage(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
        return new ByteArrayInputStream(responseBody.getBytes());
	}
	
	private static String doPost(AppContext appContext, String url, Map<String, Object> params, Map<String,File> files, String cookie, String userAgent) throws AppException {
		HttpClient httpClient = null;
		PostMethod httpPost = null;
		
		//post表单参数处理
		int length = (params == null ? 0 : params.size()) + (files == null ? 0 : files.size());
		Part[] parts = new Part[length];
		int i = 0;
        if(params != null)
        for(String name : params.keySet()){
        	parts[i++] = new StringPart(name, String.valueOf(params.get(name)), UTF_8);
        	//System.out.println("post_key==> "+name+"    value==>"+String.valueOf(params.get(name)));
        }
        if(files != null)
        for(String file : files.keySet()){
        	try {
				parts[i++] = new FilePart(file, files.get(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        	//System.out.println("post_key_file==> "+file);
        }
		
		String responseBody = "";
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, cookie, userAgent);	        
		        httpPost.setRequestEntity(new MultipartRequestEntity(parts,httpPost.getParams()));		        
		        int statusCode = httpClient.executeMethod(httpPost);
		        if(statusCode != HttpStatus.SC_OK) 
		        {
		        	throw AppException.http(statusCode);
		        }
		        else if(statusCode == HttpStatus.SC_OK) 
		        {
		            Cookie[] cookies = httpClient.getState().getCookies();
		            String tmpcookies = "";
		            for (Cookie ck : cookies) {
		                tmpcookies += ck.toString()+";";
		            }
		            //保存cookie   
	        		if(appContext != null && tmpcookies != ""){
	        			appContext.setProperty("cookie", tmpcookies);
	        			appCookie = tmpcookies;
	        		}
		        }
		     	responseBody = httpPost.getResponseBodyAsString();
		        System.out.println("XMLDATA=====>"+responseBody);
		     	break;	     	
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		
		return responseBody;
	}
	
	/**
	 * post请求URL
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException 
	 * @throws IOException 
	 * @throws  
	 */
	private static Result http_post(AppContext appContext, String url, Map<String, Object> params, Map<String,File> files) throws AppException, IOException {
        try {
			return Result.parse(_post(appContext, url, params, files));
		} catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}	
	
	private static InputStream http_post_query(AppContext appContext, String url, Map<String, Object> params) throws AppException, IOException {
        return _post(appContext, url, params, null);  
	}	
	
	/**
	 * 获取网络图片
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String url) throws AppException {
		System.out.println("image_url==> "+url);
		HttpClient httpClient = null;
		GetMethod httpGet = null;
		Bitmap bitmap = null;
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, null, null);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
		        InputStream inStream = httpGet.getResponseBodyAsStream();
		        
		        System.out.println("image_url inStream==> "+inStream.available());
		        
		        bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inStream));
		        
		        inStream.close();
		        break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		
		System.out.println("image_url result==> "+bitmap);
		
		return bitmap;
	}
	
	/**
	 * 检查版本更新
	 * @param url
	 * @return
	 */
//	public static Update checkVersion(AppContext appContext) throws AppException {
//		try{
//			return Update.parse(http_get(appContext, URLs.UPDATE_VERSION));		
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//	
	
	
	

	

	public static Result rewardApply(AppContext appContext, final String uuid, final String email, final int rewardId) throws AppException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uuid", uuid);
		params.put("email", email);
		params.put("reward_id", rewardId);
				
		try{
			return Result.parse(_post(appContext, URLs.REWARD_APPLY, params, null));		
		}catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * Register account
	 * @param uid
	 * @param type 1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
	 * @return
	 * @throws AppException
	 */
	public static Result registerService(AppContext appContext, String uuid) throws AppException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uuid", uuid);
				
		try{
			return Result.parse(_post(appContext, URLs.REGISTER, params, null));		
		}catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * Earn point
	 * @param appContext
	 * @param uuid
	 * @return
	 * @throws AppException
	 */
//	public static Result earnPoint(AppContext appContext, String uuid, String serviceId, int point) throws AppException {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uuid", uuid);
//		params.put("service_id", serviceId);
//		params.put("point", ""+point);
//				
//		try{
//			return Result.parse(_post(appContext, URLs.EARN_POINT, params, null));		
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
	
	/**
	 * Earn point
	 * @param appContext
	 * @param uuid
	 * @return
	 * @throws AppException
	 */
	public static Result audioCheck(AppContext appContext, String uuid, String audioKey, double log, double lat) throws AppException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uuid", uuid);
		params.put("audiokey", audioKey);
		params.put("log", log);
		params.put("lat", lat);
		
		try{
			return Result.parse(_post(appContext, URLs.AUDIO_CHECK, params, null));		
		}catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
	
	public static SubjectList getSubjectList(AppContext appContext, final int catalog, final int pageIndex, final int pageSize) throws AppException {
		String newUrl = _MakeURL(URLs.SUBJECT_LIST, new HashMap<String, Object>(){{
			put("catalog", catalog);
			put("pageIndex", pageIndex);
			put("pageSize", pageSize);
		}});
		
		try{
			return SubjectList.parse(http_get(appContext, newUrl));		
		}catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
	public static ServiceList getServiceList(AppContext appContext, final int catalog, final double lat, final double log, final int pageIndex, final int pageSize) throws AppException {
		int page = pageIndex + 1;
		String newUrl = null;
		if (catalog == 1) {
			String driverId = appContext.getDriverId();
			newUrl = _MakeURL(URLs.SERVICE_LIST+ "/" + driverId + "/" + (page), new HashMap<String, Object>());
		} else {
			String driverId = appContext.getDriverId();
			newUrl = _MakeURL(URLs.ORDERHISTRY_LIST+ "/" + driverId + "/" + (page), new HashMap<String, Object>());			
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", lat);
		params.put("log", log);
		params.put("cat", catalog);
		
		
		try{
			return ServiceList.parse(http_post_query(appContext, newUrl, params));		
		}catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
//	
//	public static MembershipList getMembershipList(AppContext appContext, String uuid, final int pageIndex, final int pageSize) throws AppException {
//		int page = pageIndex + 1;
//		String newUrl = _MakeURL(URLs.MEMBERSHIP_LIST+ "/1/" + (page), new HashMap<String, Object>(){{
//			put("pageIndex", pageIndex);
//			put("pageSize", pageSize);
//		}});
//		
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("uuid", uuid);
//		
//		try{
//			return MembershipList.parse(http_post_query(appContext, newUrl, params));		
//		}catch(Exception e){
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		}
//	}
//	
	
		
	/**
	 * Get service Detail
	 * @param url
	 * @param news_id
	 * @return
	 * @throws AppException
	 */
	public static Service getServiceDetail(AppContext appContext, final String order_id) throws AppException {
		String newUrl = _MakeURL(URLs.SERVICE_DETAIL +"/"+ order_id, new HashMap<String, Object>(){{
			put("id", order_id);
		}});
		
		try{
			return Service.parse(http_get(appContext, newUrl));			
		}catch(Exception e){
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
	
	
	 static class FlushedInputStream extends FilterInputStream {
	        public FlushedInputStream(InputStream inputStream) {
	            super(inputStream);
	        }

	        @Override
	        public long skip(long n) throws IOException {
	            long totalBytesSkipped = 0L;
	            while (totalBytesSkipped < n) {
	                long bytesSkipped = in.skip(n - totalBytesSkipped);
	                if (bytesSkipped == 0L) {
	                    int b = read();
	                    if (b < 0) {
	                        break;  // we reached EOF
	                    } else {
	                        bytesSkipped = 1; // we read one byte
	                    }
	                }
	                totalBytesSkipped += bytesSkipped;
	            }
	            return totalBytesSkipped;
	        }
	    }
	 
	 
	 class RetreiveFeedTask extends AsyncTask<String, Void, Object> {

		    private Exception exception;

		    protected Object doInBackground(String... urls) {
		        try {
		            //..;
		        } catch (Exception e) {
		            this.exception = e;
		            return null;
		        }
		        return null;
		    }

		    protected void onPostExecute(Object feed) {
		        // TODO: check this.exception 
		        // TODO: do something with the feed
		    }
		 }

		
}
