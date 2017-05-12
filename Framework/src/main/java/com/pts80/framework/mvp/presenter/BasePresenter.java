package com.pts80.framework.mvp.presenter;

import android.content.Context;

/**
 * Created by Kang on 2017/3/14.
 */

public interface BasePresenter<V> {

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 绑定视图
     *
     * @param iView
     */
    void attachView(V iView);

    /**
     * 解绑视图
     *
     * @param iView
     */
    void detachView(V iView);

    /**
     * 获取视图
     *
     * @return
     */
    V getView();
}
