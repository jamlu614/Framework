package com.pts80.framework.utils;

import java.util.LinkedList;

import android.app.Activity;

public class CloseAllActivities {

	private static LinkedList<Activity> activities = new LinkedList<Activity>();
	public static Activity curActivity;

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
}
