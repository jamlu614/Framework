package com.pts80.framework.mvp.presenter.impl;

import android.content.Context;

import com.pts80.framework.mvp.presenter.BasePresenter;
import com.pts80.framework.mvp.view.BaseIView;

import java.lang.ref.WeakReference;

/**
 * Author ljb
 * Created at 2016/10/14.
 * Description
 * p层基类
 */

public class BaseFragmentPresenterImpl<V extends BaseIView> implements BasePresenter<V> {
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
    public void attachView(V iView) {
    }

    @Override
    public void detachView(V iView) {
    }

    @Override
    public V getView() {
        return mView.get();
    }
}
