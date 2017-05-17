package com.example.rxjava.frameworkdemo.network;

import com.jamlu.framework.model.bean.BaseBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ljb on 2017/4/5.
 * 看房网络服务
 */

public interface WeatherService {
    /**
     * 获取新浪数据
     *
     * @return
     */
    @GET("weather?location=嘉兴&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ")
    Observable<BaseBean> getWeatherData();

}
