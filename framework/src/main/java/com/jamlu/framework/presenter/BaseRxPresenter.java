package com.jamlu.framework.presenter;

import android.content.Context;

import rx.Subscription;

/**
 * Created by Kang on 2017/3/14.
 */

public interface BaseRxPresenter<V> {

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();


    /**
     * 获取视图
     *
     * @return
     */
    V getView();

    /**
     * 订阅
     */
    void addSubscription(Subscription s);

    /**
     * 取消订阅
     */
    void unSubscribe();
}
