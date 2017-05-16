package com.example.rxjava.frameworkdemo.network;

import com.google.gson.GsonBuilder;
import com.pts80.framework.base.BaseApplication;
import com.pts80.framework.net.retrofit.NetworkManager;

/**
 * Author ljb
 * Created at 2016/11/21.
 * Description
 * 网络请求工具
 */

public class NetworkUtils {

    /**
     * 解析json数据
     *
     * @return
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        return new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create().fromJson(json, clazz);
    }

    /**
     * 获取看房服务
     * @return
     */
    public static WeatherService getWeatherService() {
        return NetworkManager.getInstance(BaseApplication.getContext()).create(WeatherService.class);
    }

}
