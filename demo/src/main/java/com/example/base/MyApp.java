package com.example.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.jamlu.framework.base.BaseApplication;

/**
 * Created by ljb on 2017/5/31.
 */

public class MyApp extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
