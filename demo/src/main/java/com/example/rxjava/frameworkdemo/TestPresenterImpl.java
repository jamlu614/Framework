package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.jamlu.framework.base.BaseRxActivity;
import com.jamlu.framework.model.bean.BaseBean;
import com.jamlu.framework.newwork.NetworkSubscriber;
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
                        getView().loadView(BaseRxActivity.STATUS_SUCCESS);
//                        getView().loadView(BaseRxActivity.STATUS_ERROR);
                    }

                    @Override
                    public void onFailure(int httpCode, String msg) {
                        super.onFailure(httpCode, msg);
                        if (httpCode == NetworkSubscriber.RESPOND_NO_NETWORK) {
                            getView().loadView(BaseRxActivity.STATUS_NO_NETWORK);
                        } else {
                            getView().loadView(BaseRxActivity.STATUS_SUCCESS);
                        }
                    }
                }));
        addSubscription(subscribe);
    }
}
