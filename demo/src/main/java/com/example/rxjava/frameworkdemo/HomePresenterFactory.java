package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.jamlu.framework.presenter.inf.BaseRxPresenter;
import com.jamlu.framework.ui.view.BaseIView;

/**
 * Created by ljb on 2017/5/16.
 */

public class HomePresenterFactory extends PresenterFactory {
    public final static int TYPE_TEST = 1;

    private static volatile HomePresenterFactory sFactory;

    public static HomePresenterFactory getInstance() {
        if (sFactory == null) {
            synchronized (new Object()) {
                if (sFactory == null) {
                    return new HomePresenterFactory();
                } else {
                    return sFactory;
                }
            }
        }
        return sFactory;
    }

    @Override
    public BaseRxPresenter create(Context content, BaseIView iView, int type) {
        switch (type) {
            case TYPE_TEST:
                return new TestPresenterImpl(content, ((ITestView) iView));
            default:
                return null;
        }
    }
}
