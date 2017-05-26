package com.example.rxjava.frameworkdemo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jamlu.framework.base.BaseRxActivity;


public class TestActivity extends BaseRxActivity<TestPresenter> implements ITestView {

    private ImageView mArrow;
    private boolean mIsUp;
    private BlankFragment mBlankFragment;
    private Button mBtnChange;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenterFactory(this,this).create();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitleRes(R.layout.title_textview_left_icon);
        mBtnChange = (Button) findViewById(R.id.btn_change);
    }

    @Override
    protected void setupTitleBar(ViewGroup rootView) {
        super.setupTitleBar(rootView);
        mArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_requestNetwork:
                getPresenter().getPageData();
                break;
            case R.id.btn_toFragment:
                if(!mBlankFragment.isAdded())
                getSupportFragmentManager().beginTransaction().add(R.id.content, mBlankFragment).commit();
                break;
            case R.id.btn_change:
                mBtnChange.setText("已改变");
                break;
            default:
        }
    }

    @Override
    public void initData() {
        setTitle("测试");
        mBlankFragment = BlankFragment.newInstance();
        mBtnChange.setText("初始化改变");
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
//
//    @Override
//    protected void onReload() {
//        getPresenter().getPageData();
//    }
}
