package com.hkgoodvision.gvpos.dao.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 数据操作结果实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Result extends Base {

	private int errorCode;
	private String errorMessage;
	private String returnCode;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	
	public boolean OK() {
		return errorCode == 1;
	}

	/**
	 * 解析调用结果
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static Result parse(InputStream stream) throws Exception {

		Result service = new Result();

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONArray jArray = jObject.getJSONArray("result");

		// get the List Array
		int i = 0;

		JSONObject oneObject = jArray.getJSONObject(0);
		// Pulling items from the array
		int errorCode = oneObject.getInt("error_code");
		String errorMessage = oneObject.getString("error_message");
		String returnCode = oneObject.getString("return_code");
		String param1 = null;
		String param2 = null;
		String param3 = null;
		String param4 = null;
		try {
			param1 = oneObject.getString("param1");
			param2 = oneObject.getString("param2");		
			param3 = oneObject.getString("param3");		
			param4 = oneObject.getString("param4");
		} catch (JSONException  e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		

		service.setErrorCode(errorCode);
		service.setErrorMessage(errorMessage);
		service.setReturnCode(returnCode);
		service.setParam1(param1);
		service.setParam2(param2);
		service.setParam3(param3);
		service.setParam4(param4);

		return service;

	}

	public int getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	@Override
	public String toString(){
		return String.format("RESULT: CODE:%d,MSG:%s", errorCode, errorMessage);
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

}
