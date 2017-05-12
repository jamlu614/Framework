package com.pts80.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
/**
 * 获取设备信息
 * 
 * @author Ben
 *
 */

public class AppDevice {
	private static AppDevice myInstance;


	private AppDevice(Activity act) {
	}

	public static void init(Activity act) {
		myInstance = new AppDevice(act);
	}

	public static AppDevice getInstance() {
		return myInstance;
	}
	/**
	 * 获取设备IMSI
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();// IMSI
		if (imsi == null) {
			imsi = "-";
		}
		return imsi;
	}

	/**
	 * 获取手机型号
	 */
	public static String getPhoneType() {
		return Build.MODEL;
	}

	/**
	 * 获取设备分辨率
	 * @param act
	 * @return
	 */
	public static String getPhoneResolution(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels + "*" + dm.heightPixels;
	}

	/**
	 * 获取设备分辨率
	 * @param act
	 * @return
	 */
	public static int[] getPhoneResolutionToInt(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new int[] { dm.widthPixels, dm.heightPixels };
	}

	

	/**
	 *  获取应用版本名
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			versionName = "";
		}
		return versionName;
	}

	/**
	 * 获取程序版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			versionCode = 0;
		}
		return versionCode;
	}

	

	/**
	 *判断系统版本是否大于3.0
	 * */
	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 *判断系统版本是否大于3.1
	 * */
	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/**
	 * 判断系统版本是否大于4.0
	 * */
	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**
	 *判断系统版本是否大于4.2
	 * */
	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	/**
	 *判断系统版本是否大于5.0
	 * */
	public static boolean hasAndroidL() {
		return Build.VERSION.SDK_INT >= 20;
	}

	/**
	 * @param context
	 * @return 判断网络是否正常
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}

			}
		}
		return false;
	}

	public static boolean hasSdcard() {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}
		return false;
	}
	
}
