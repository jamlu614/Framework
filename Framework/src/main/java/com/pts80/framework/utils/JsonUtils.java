package com.pts80.framework.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	/**
	 * JSON中getString() 和optString()区别：
	 * 简单来说就是optString会在得不到你想要的值时候返回空字符串""，而getString会抛出异常。
	 *
	 */
	
	/**获取字符串值*/
	public static String getJsonStr(JSONObject obj, String key) {
		String result = "";
		if(obj.has(key)){
			result = obj.optString(key);
		}
		return result;
	}
	
	
	/**获取浮点型数值*/
	public static double getJsonDouble(JSONObject obj, String key) {
		double result = -1;
		if(obj.has(key)){
			result = obj.optDouble(key);
		}
		return result;
	}
	
	/**获取int值*/
	public static int getJsonInt(JSONObject obj, String key) {
		int result = -1;
		if(obj.has(key)){
			result = obj.optInt(key);
		}
		return result;
	}
	
	/**获取int值*/
	public static long getJsonLong(JSONObject obj, String key) {
		long result = -1;
		if(obj.has(key)){
			result = obj.optLong(key);
		}
		return result;
	}

	/**获取int值*/
	public static boolean getJsonBoolean(JSONObject obj, String key) {
		boolean result = false;
		if(obj.has(key)){
			result = obj.optBoolean(key);
		}
		return result;
	}
	
	/**获取JSONObjcet值*/
	public static JSONObject getJSONObject(JSONObject obj, String key) {
		JSONObject result = null;
		if(obj.has(key)){
			result = obj.optJSONObject(key);
		}
		return result;
	}
	
	/**获取JSONObjcet值*/
	public static JSONArray getJSONObjectArray(JSONObject obj, String key) {
		JSONArray result = null;
		if(obj.has(key)){
			result = obj.optJSONArray(key);
		}
		return result;
	}
	
	/**获取get值*/
	public static Object getJsonGet(JSONObject obj, String key) {
		Object result = null;
		if(obj.has(key)){
			result = obj.opt(key);
		}
		return result;
	}
}
