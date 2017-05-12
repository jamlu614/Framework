package com.pts80.framework.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pts80.framework.presenter.inf.BaseRxPresenter;
import com.pts80.framework.ui.view.BaseIView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Fragment基类
 */
public abstract class BaseRxFragment<V extends BaseIView, T extends BaseRxPresenter<V>> extends Fragment {
    Unbinder unbinder;
    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), setLayoutResID(), null);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        presenter = createPresenter();
        initData();
        initEvent();
        return view;
    }


    /**
     * 实现setContentView()
     */
    public abstract int setLayoutResID();

    /**
     * 初始化布局组件
     */
    protected void initViews(View view) {
    }

    /**
     * 初始化P层实现类
     *
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 初始化布局组件的数据
     */
    public abstract void initData();

    /**
     * 初始化布局组件的监听事件
     */
    public abstract void initEvent();

    @Override
    public void onStop() {
        super.onStop();
        //p层取消订阅
        presenter.unSubscribe();
        //v层取消订阅
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 获取presenter
     *
     * @return
     */
    public T getPresenter() {
        return presenter;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(@NonNull String title) {
        if (getActivity() instanceof BaseRxActivity) {
            ((BaseRxActivity) getActivity()).setTitle(title);
        }
    }


    private CompositeSubscription mCompositeSubscription; //这个类的内部是由Set<Subscription> 维护订阅者

    //添加已订阅的订阅器
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

}
