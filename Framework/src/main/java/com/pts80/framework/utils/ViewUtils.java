package com.pts80.framework.utils;

import android.app.Activity;
import android.view.View;

/**
 * 不需要ViewInject，简化你的findViewById
 * 
 * @author Kangyu
 *
 */
public class ViewUtils {
	public static <T extends View> T findViewById(Activity context, int id) {
		return (T) context.findViewById(id);
	}

	public static <T extends View> T findViewById(View rootView, int id) {
		return (T) rootView.findViewById(id);
	}
}
