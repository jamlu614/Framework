package com.example.rxjava.frameworkdemo;

import android.view.View;

import com.pts80.framework.base.BaseRxActivity;

public class TestActivity extends BaseRxActivity<ITestView, TestPresenter> implements ITestView {


    @Override
    public int setLayoutResID() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenterImpl2(this, this);
    }

    @Override
    public void initData() {
        setTitle("测试");
    }

    @Override
    public void initEvent() {

    }

    public void doClick(View view) {
        getPresenter().getPageData();
    }
}
