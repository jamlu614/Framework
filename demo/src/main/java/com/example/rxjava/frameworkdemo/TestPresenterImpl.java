package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.example.rxjava.frameworkdemo.network.NetworkSubscriber;
import com.jamlu.framework.model.bean.BaseBean;
import com.jamlu.framework.presenter.BaseRxPresenterImpl;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ljb on 2017/4/6.
 */

public class TestPresenterImpl extends BaseRxPresenterImpl<ITestView> implements TestPresenter {
    private TestModel mModel;

    public TestPresenterImpl(Context context, ITestView iView) {
        super(context, iView);
        mModel = new TestModelFactory().create();
    }

    /**
     *
     */
    public void getPageData() {
        Subscription subscribe = mModel.getSinaTouTiao()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkSubscriber<>(getContext(), false, new NetworkSubscriber.Callback<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
        addSubscription(subscribe);
    }
}
