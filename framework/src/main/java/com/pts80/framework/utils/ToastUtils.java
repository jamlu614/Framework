package com.pts80.framework.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * 简单封装Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
