package com.example.ui.fragment;

import com.jamlu.framework.base.BaseRxFragment;
import com.jamlu.framework.presenter.BaseRxPresenter;

/**
 * Created by ljb on 2017/5/31.
 * 首页抽象片段
 */

public abstract class AbstractHomeFragment<T extends BaseRxPresenter> extends BaseRxFragment<T> implements IHomeView {
}
