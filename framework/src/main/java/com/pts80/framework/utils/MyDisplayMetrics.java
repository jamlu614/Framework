package com.pts80.framework.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕信息相关类（做成单例，在application中实例化，避免每次需要时都去实例化）
 * @author zhang
 *
 */
public class MyDisplayMetrics {

	private static DisplayMetrics mDis;
	public static void init(Context context){
		mDis = context.getResources().getDisplayMetrics();
	}
	
	public static DisplayMetrics getDisPlayMetrics(){
		return mDis;
	}
	
	public static float getDensity(){
		return mDis.density;
	}
	
	public static float dpToPx(float dp) {
		return dp * getDensity();
	}

	public static int dpToPx(int dp) {
		return (int) (dp * getDensity());
	}

	public static float pxToDp(float px) {
		return px / getDensity();
	}

	public static int dpToPxInt(float dp) {
		return (int) (dpToPx(dp) + 0.5f);
	}

	public static int pxToDpCeilInt(float px) {
		return (int) (pxToDp(px) + 0.5f);
	}
	
	public static int px2sp(float pxValue) {
		  return (int) (pxValue / mDis.scaledDensity + 0.5f);
	}

	public static int sp2px(float spValue) {
		  return (int) (spValue * mDis.scaledDensity + 0.5f);
	}
}
