package com.example.rxjava.frameworkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pts80.framework.base.BaseRxActivity;

public class TestActivity extends BaseRxActivity<ITestView, TestPresenter> implements ITestView {


    private ImageView mArrow;
    private boolean mIsUp;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenterImpl2(this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitleRes(R.layout.title_textview_left_icon);
    }

    @Override
    public void initData() {
        setTitle("测试");
        mArrow = (ImageView) getToolbar().findViewById(R.id.iv_arrow);

    }

    @Override
    public void initEvent() {

    }

    public void doClick(View view) {
        getPresenter().getPageData();
        if (mIsUp) {
            mArrow.setImageResource(R.drawable.ic_arrow_up_white);
        } else {
            mArrow.setImageResource(R.drawable.ic_arrow_down_white);
        }
        mIsUp = !mIsUp;
    }

}
