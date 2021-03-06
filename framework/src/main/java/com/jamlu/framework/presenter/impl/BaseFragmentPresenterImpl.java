package com.jamlu.framework.presenter.impl;

import android.content.Context;

import com.jamlu.framework.presenter.BaseRxPresenter;
import com.jamlu.framework.ui.view.BaseIView;

import java.lang.ref.WeakReference;

/**
 * Author ljb
 * Created at 2016/10/14.
 * Description
 * p层基类
 */

public abstract class BaseFragmentPresenterImpl<V extends BaseIView> implements BaseRxPresenter<V> {
    public WeakReference<V> mView;
    private Context context;

    public BaseFragmentPresenterImpl(Context context, V iView) {
        mView = new WeakReference<>(iView);
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public V getView() {
        return mView.get();
    }
}
