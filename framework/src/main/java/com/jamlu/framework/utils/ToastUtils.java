package com.jamlu.framework.utils;

import android.support.annotation.IntDef;
import android.widget.Toast;

import com.jamlu.framework.base.BaseApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toast工具类
 */
public class ToastUtils {
    /**
     */
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     */
    public static final int LENGTH_LONG = 1;
    private static Toast toast;

    /**
     * 显示Toast
     *
     * @param message
     */
    public static void show(String message) {
        show(message, LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param message
     */
    public static void show(String message, @Duration int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), message, duration);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
