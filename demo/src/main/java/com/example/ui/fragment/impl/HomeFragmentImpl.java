package com.example.ui.fragment.impl;

import android.view.View;
import android.widget.TextView;

import com.example.factory.presenterfactory.HomePresenterFactory;
import com.example.presenter.HomePresenter;
import com.example.ui.fragment.AbstractHomeFragment;
import com.example.ui.fragment.IHomeView;
import com.framework.example.R;

import butterknife.BindView;

/**
 * Created by ljb on 2017/05/31
 * 首页片段实现类
 */
public class HomeFragmentImpl extends AbstractHomeFragment<HomePresenter> implements IHomeView {


    @BindView(R.id.tv_content)
    TextView mTvContent;

    protected int setLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenterFactory(getContext(),this).create();
    }

    @Override
    protected void initData() {
        String content = getArguments().getString("content");
        mTvContent.setText(content);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void onReload() {
        getPresenter().getPageData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
