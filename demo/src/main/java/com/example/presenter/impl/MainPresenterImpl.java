package com.example.presenter.impl;

import android.content.Context;

import com.jamlu.framework.presenter.impl.BaseRxPresenterImpl;
import com.example.presenter.MainPresenter;
import com.example.ui.activity.IMainView;

/**
 * Created by ljb on 2017/4/6.
 * 主体p层实现类
 */

public class MainPresenterImpl extends BaseRxPresenterImpl<IMainView> implements MainPresenter {

    public MainPresenterImpl(Context context, IMainView iView) {
        super(context, iView);
    }
}
