package com.jamlu.framework.network;

import com.google.gson.GsonBuilder;
import com.jamlu.framework.utils.helper.NullStringToEmptyAdapterFactory;

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
