package com.pts80.framework.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static BaseApplication mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }


}