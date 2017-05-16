package com.pts80.framework.net.retrofit;

import com.google.gson.GsonBuilder;
import com.pts80.framework.utils.NullStringToEmptyAdapterFactory;

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
}
