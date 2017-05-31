package com.example.network;

import com.jamlu.framework.base.BaseApplication;
import com.jamlu.framework.network.NetworkManager;

import retrofit2.Retrofit;

/**
 * Created by ljb on 2017/5/31.
 */

public class ServiceManager {

    /**
     * 获取网络请求管理器
     * @return
     */
    private static Retrofit getNetworkManager() {
        return NetworkManager.getInstance(BaseApplication.getContext());
    }

    /**
     * 获取看房服务
     * @return
     */
    public static HomeService getWeatherService() {
        return getNetworkManager().create(HomeService.class);
    }
}
