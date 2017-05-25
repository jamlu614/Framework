package com.example.rxjava.frameworkdemo;

import com.example.rxjava.frameworkdemo.network.NetworkUtils;
import com.jamlu.framework.model.AbstractModel;
import com.jamlu.framework.model.bean.BaseBean;

import rx.Observable;

/**
 * Created by ljb on 2017/4/6.
 */

class TestModelImpl extends AbstractModel implements TestModel {

    /**
     * 获取新浪头条
     *
     * @return
     */
    @Override
    public Observable<BaseBean> getSinaTouTiao() {
        return NetworkUtils
                .getWeatherService()
                .getWeatherData();
    }
}
