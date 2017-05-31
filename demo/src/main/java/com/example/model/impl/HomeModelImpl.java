package com.example.model.impl;

import com.jamlu.framework.model.AbstractModel;
import com.jamlu.framework.model.bean.BaseBean;
import com.example.model.HomeModel;
import com.example.network.ServiceManager;

import rx.Observable;

/**
 * Created by ljb on 2017/4/6.
 */

public class HomeModelImpl extends AbstractModel implements HomeModel {

    /**
     * 获取新浪头条
     *
     * @return
     */
    @Override
    public Observable<BaseBean> getSinaTouTiao() {
        return ServiceManager
                .getWeatherService()
                .getWeatherData();
    }
}
