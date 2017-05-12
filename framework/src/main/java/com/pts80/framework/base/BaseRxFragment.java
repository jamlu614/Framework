package com.pts80.framework.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pts80.framework.R;
import com.pts80.framework.mvp.presenter.BaseRxPresenter;
import com.pts80.framework.mvp.view.BaseIView;

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
    private TextView mTxtTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), setLayoutResID(), null);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        setupTitleBar();
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
     * 初始化标题栏
     */
    private void setupTitleBar() {
        mTxtTitle = (TextView) getActivity().getWindow().getDecorView().findViewById(R.id.txt_title_title_bar);
        ImageView imgLeft = (ImageView) getActivity().getWindow().getDecorView().findViewById(R.id.img_back_title_bar);
        if (imgLeft != null && imgLeft.getVisibility() == View.VISIBLE) {
            imgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
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
        if (mTxtTitle != null) {
            mTxtTitle.setText(title);
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


//    /***
//     * 创建加载对话框，重写该方法可以自定义加载对话框
//     *
//     * @return
//     */
//    public Dialog createProgressDialog() {
//        return new MyProgressDialog(getActivity(), R.style.ProgressDialogStyle);
//    }

    /**
     * 避免重复点击事件
     * 避免点击速度过快
     */
    private long system_time;

    public boolean getToOnClick() {
        if (System.currentTimeMillis() - system_time > 2000) {
            system_time = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

}
