package com.example.rxjava.frameworkdemo;


import com.jamlu.framework.presenter.inf.BaseRxPresenter;

/**
 * Created by ljb on 2017/4/6.
 */

interface TestPresenter extends BaseRxPresenter<ITestView> {
    /**
     * 获取页面数据
     */
    void getPageData();
}
