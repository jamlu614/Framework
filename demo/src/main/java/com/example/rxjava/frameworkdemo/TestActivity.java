package com.example.rxjava.frameworkdemo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jamlu.framework.base.BaseRxActivity;


public class TestActivity extends BaseRxActivity<ITestView, TestPresenter> implements ITestView {

    private ImageView mArrow;
    private boolean mIsUp;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter createPresenter() {
        HomePresenterFactory factory = HomePresenterFactory.getInstance();
        return (TestPresenter) factory.create(this, this, HomePresenterFactory.TYPE_TEST);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitleRes(R.layout.title_textview_left_icon);
    }

    @Override
    protected void setupTitleBar(ViewGroup rootView) {
        super.setupTitleBar(rootView);
        mArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
    }

    @Override
    public void initData() {
        setTitle("测试");
    }

    @Override
    public void initEvent() {
        getTitleLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsUp) {
                    mArrow.setImageResource(R.drawable.ic_arrow_up_white);
                } else {
                    mArrow.setImageResource(R.drawable.ic_arrow_down_white);
                }
                mIsUp = !mIsUp;
            }
        });
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
