package com.example.factory.presenterfactory;

import android.content.Context;

import com.jamlu.framework.base.AbstractPresenterFactory;
import com.jamlu.framework.base.Factory;
import com.example.presenter.HomePresenter;
import com.example.presenter.impl.HomePresenterImpl;
import com.example.ui.fragment.IHomeView;


/**
 * Created by ljb on 2017/5/16.
 * /首页片段p层工厂
 */

public class HomePresenterFactory extends AbstractPresenterFactory<IHomeView> implements Factory<HomePresenter> {


    public HomePresenterFactory(Context context, IHomeView IView) {
        super(context, IView);
    }

    @Override
    public HomePresenter create() {
        return new HomePresenterImpl(getContext(), getIView());
    }

}
