package com.pts80.framework.utils;

import android.app.Activity;

import java.util.LinkedList;

public class ActivityUtils {

	private static LinkedList<Activity> activities = new LinkedList<Activity>();

	public static void add(Activity activity) {
		activities.add(activity);
	}

	public static void remove(Activity activity) {
		activities.remove(activity);
	}

	public static void close() {
		Activity activity;
		while (activities.size() != 0) {
			activity = activities.poll();
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	/**
	 * 获取当前activity
	 * @return
	 */
	public static Activity getCurActivity() {
		if (activities.size() > 0) {
			return activities.get(activities.size() - 1);
		}
		return null;
	}


	/**
	 * 关闭当前页面跳转到某个页面
	 */
	public static void closeToOther(Class<?> clazz) {
		Activity activity;
		while (activities.size() != 0) {
			activity = activities.poll();
			if (!activity.isFinishing()) {
				if (!activity.getClass().getSimpleName().equals(clazz.getSimpleName())) {
					activity.finish();
				} else {
					break;
				}
			}
		}
	}
}
