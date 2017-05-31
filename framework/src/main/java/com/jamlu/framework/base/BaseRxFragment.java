package com.jamlu.framework.base;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamlu.framework.presenter.BaseRxPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.jamlu.framework.base.BaseRxActivity.STATUS_ERROR;
import static com.jamlu.framework.base.BaseRxActivity.STATUS_NO_NETWORK;
import static com.jamlu.framework.base.BaseRxActivity.STATUS_SUCCESS;

/**
 * Created by ljb on 2017/05/26
 * Fragment基类
 */
public abstract class BaseRxFragment<T extends BaseRxPresenter> extends Fragment {
    Unbinder unbinder;
    protected T presenter;

    @IntDef({STATUS_SUCCESS, STATUS_ERROR, STATUS_NO_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageStatus {
    }

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
     * 绘制页面
     *
     * @param status
     */
    public void loadView(@PageStatus int status) {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseRxActivity) {
            ((BaseRxActivity) activity).loadView(status);
        }
    }

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
    protected T getPresenter() {
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


    /**
     * 如果要响应重新加载按钮，请重写该方法
     */
    protected void onReload() {
    }


    /**
     * 实现setContentView()
     */
    protected abstract int setLayoutResID();

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
    protected abstract void initData();

    /**
     * 初始化布局组件的监听事件
     */
    protected void initEvent() {
    }

    ;

}
