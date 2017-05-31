package com.example.presenter;


import com.jamlu.framework.presenter.BaseRxPresenter;
import com.example.ui.fragment.IHomeView;

/**
 * Created by ljb on 2017/4/6.
 * 首页片段p层接口
 */

public interface HomePresenter extends BaseRxPresenter<IHomeView> {
    /**
     * 获取页面数据
     */
    void getPageData();
}
