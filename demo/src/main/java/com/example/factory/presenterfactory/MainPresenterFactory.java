package com.example.factory.presenterfactory;

import android.content.Context;

import com.jamlu.framework.base.AbstractPresenterFactory;
import com.jamlu.framework.base.Factory;
import com.example.presenter.MainPresenter;
import com.example.presenter.impl.MainPresenterImpl;
import com.example.ui.activity.IMainView;


/**
 * Created by ljb on 2017/5/16.
 * /主体p层工厂
 */

public class MainPresenterFactory extends AbstractPresenterFactory<IMainView> implements Factory<MainPresenter> {


    public MainPresenterFactory(Context context, IMainView IView) {
        super(context, IView);
    }

    @Override
    public MainPresenter create() {
        return new MainPresenterImpl(getContext(), getIView());
    }

}
