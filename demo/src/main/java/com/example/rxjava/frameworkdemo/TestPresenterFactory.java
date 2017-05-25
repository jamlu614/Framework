package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.jamlu.framework.base.AbstractFactory;


/**
 * Created by ljb on 2017/5/16.
 *
 */

public class TestPresenterFactory extends AbstractFactory<TestPresenter> {

    private Context mContext;
    private ITestView mIView;

    public TestPresenterFactory(Context context, ITestView IView) {
        mContext = context;
        mIView = IView;
    }

    @Override
    public TestPresenter create() {
        return new TestPresenterImpl(mContext, mIView);
    }
}
