package com.jamlu.framework.presenter;

import android.content.Context;

import com.jamlu.framework.presenter.inf.BaseRxPresenter;
import com.jamlu.framework.ui.view.BaseIView;

import java.lang.ref.WeakReference;

/**
 * Author ljb
 * Created at 2016/10/14.
 * Description
 * p层基类
 */

public abstract class BasePresenterImpl<V extends BaseIView> implements BaseRxPresenter<V> {
    public WeakReference<V> mView;
    private Context context;

    public BasePresenterImpl(Context context, V iView) {
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
