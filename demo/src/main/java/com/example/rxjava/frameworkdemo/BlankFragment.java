package com.example.rxjava.frameworkdemo;

import android.view.View;
import android.widget.Button;

import com.jamlu.framework.base.BaseRxFragment;

public class BlankFragment extends BaseRxFragment<TestPresenter> implements View.OnClickListener, ITestView {

    private Button mRequestNetwork;

    public BlankFragment() {
    }


    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void initViews(View view) {
        mRequestNetwork = (Button) view.findViewById(R.id.btn_requestNetwork);
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenterImpl(getContext(), this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mRequestNetwork.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_requestNetwork:
                getPresenter().getPageData();
                break;
        }
    }

    @Override
    protected void onReload() {
        getPresenter().getPageData();
    }
}
