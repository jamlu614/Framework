package com.jamlu.framework.base;

import android.content.Context;

/**
 * Created by ljb on 2017/5/26.
 * 抽象presenter工厂
 */

public abstract class AbstractPresenterFactory<V> {

    private Context mContext;
    private V mIView;

    public AbstractPresenterFactory(Context context, V IView) {
        mContext = context;
        mIView = IView;
    }

    public Context getContext() {
        return mContext;
    }

    public V getIView() {
        return mIView;
    }
}
