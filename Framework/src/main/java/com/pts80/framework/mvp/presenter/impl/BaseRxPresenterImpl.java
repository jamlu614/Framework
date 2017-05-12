package com.pts80.framework.mvp.presenter.impl;

import android.content.Context;

import com.pts80.framework.mvp.presenter.BaseRxPresenter;
import com.pts80.framework.mvp.view.BaseIView;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author ljb
 * Created at 2016/10/14.
 * Description
 * p层基类
 */

public class BaseRxPresenterImpl<V extends BaseIView> implements BaseRxPresenter<V> {
    public WeakReference<V> mView;
    private Context context;
    private CompositeSubscription mCompositeSubscription; //这个类的内部是由Set<Subscription> 维护订阅者

    public BaseRxPresenterImpl(Context context, V iView) {
        this.context = context;
        mView = new WeakReference<>(iView);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public V getView() {
        return mView.get();
    }

    //提供给子类的方法
    @Override
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
