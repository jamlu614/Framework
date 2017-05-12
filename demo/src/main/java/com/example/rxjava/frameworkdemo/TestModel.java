package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.example.rxjava.frameworkdemo.network.NetworkUtils;
import com.pts80.framework.mvp.bean.BaseBean;

import rx.Observable;

/**
 * Created by ljb on 2017/4/6.
 */

class TestModel extends BaseModel {

    TestModel(Context context) {
        super(context);
    }

    /**
     * 获取新浪头条
     * @return
     */
    Observable<BaseBean> getSinaTouTiao() {
       return NetworkUtils
                .getWeatherService()
                .getWeatherData();
    }
}
