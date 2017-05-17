package com.jamlu.framework.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static BaseApplication mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static SPUtils mSpUtils;

    public static SPUtils getSpUtils() {
        return mSpUtils;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        //初始化工具类
        Utils.init(this);
        mSpUtils = new SPUtils(getPackageName());
    }

    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }


}