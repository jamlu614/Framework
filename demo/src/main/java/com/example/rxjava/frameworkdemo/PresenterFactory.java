package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.pts80.framework.presenter.inf.BaseRxPresenter;
import com.pts80.framework.ui.view.BaseIView;

/**
 * Created by ljb on 2017/5/16.
 */

public abstract class PresenterFactory {
    public abstract BaseRxPresenter create(Context content, BaseIView iView, int type);
}
