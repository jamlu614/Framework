package com.example.presenter.impl;

import android.content.Context;

import com.jamlu.framework.base.BaseRxActivity;
import com.jamlu.framework.model.bean.BaseBean;
import com.jamlu.framework.network.NetworkSubscriber;
import com.jamlu.framework.presenter.impl.BaseRxPresenterImpl;
import com.example.factory.modelfacory.HomeModelFactory;
import com.example.model.HomeModel;
import com.example.presenter.HomePresenter;
import com.example.ui.fragment.IHomeView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ljb on 2017/4/6.
 * 首页片段p层实现类
 */

public class HomePresenterImpl extends BaseRxPresenterImpl<IHomeView> implements HomePresenter {
    private HomeModel mModel;

    public HomePresenterImpl(Context context, IHomeView iView) {
        super(context, iView);
        mModel = new HomeModelFactory().create();
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
