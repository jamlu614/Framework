package com.example.rxjava.frameworkdemo;

import com.example.rxjava.frameworkdemo.network.NetworkUtils;
import com.pts80.framework.model.BaseRxModel;
import com.pts80.framework.model.bean.BaseBean;

import rx.Observable;

/**
 * Created by ljb on 2017/4/6.
 */

class TestModel extends BaseRxModel {

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
