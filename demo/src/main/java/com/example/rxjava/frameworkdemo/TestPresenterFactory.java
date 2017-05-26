package com.example.rxjava.frameworkdemo;

import android.content.Context;

import com.jamlu.framework.base.AbstractPresenterFactory;
import com.jamlu.framework.base.Factory;


/**
 * Created by ljb on 2017/5/16.
 */

public class TestPresenterFactory extends AbstractPresenterFactory<ITestView> implements Factory<TestPresenter> {


    public TestPresenterFactory(Context context, ITestView IView) {
        super(context, IView);
    }

    @Override
    public TestPresenter create() {
        return new TestPresenterImpl(getContext(), getIView());
    }

}
