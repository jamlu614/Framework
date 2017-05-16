package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.example.rxjava.frameworkdemo.network.NetworkSubscriber;
import com.pts80.framework.model.bean.BaseBean;
import com.pts80.framework.presenter.BaseRxPresenterImpl;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ljb on 2017/4/6.
 */

public class TestPresenterImpl2 extends BaseRxPresenterImpl<ITestView> implements TestPresenter {
    private TestModel mModel;

    public TestPresenterImpl2(Context context, ITestView iView) {
        super(context, iView);
        mModel = new TestModel();
    }

    /**
     *
     */
    public void getPageData() {
        //获取订阅器
        Subscription subscribe = mModel.getSinaTouTiao()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //订阅
                .subscribe(new NetworkSubscriber<>(getContext(), false, new NetworkSubscriber.Callback<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
        //添加订阅器到订阅池
        addSubscription(subscribe);
    }
}
