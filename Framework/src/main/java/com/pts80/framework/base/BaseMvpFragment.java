package com.pts80.framework.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pts80.framework.R;
import com.pts80.framework.widget.MyProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 */
public abstract class BaseMvpFragment extends Fragment {
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), setLayoutResID(), null);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initDatas();
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
    public abstract void initViews(View view);

    /**
     * 初始化布局组件的数据
     */
    public abstract void initDatas();

    /**
     * 初始化布局组件的监听事件
     */
    public abstract void initEvent();

    /***
     * 创建加载对话框，重写该方法可以自定义加载对话框
     *
     * @return
     */
    public Dialog createProgressDialog() {
        return new MyProgressDialog(getActivity(), R.style.ProgressDialogStyle);
    }

    /**
     * 避免重复点击事件
     * 避免点击速度过快
     */
    private long system_time;

    public boolean getToOnClick(){
        if (System.currentTimeMillis() - system_time > 2000) {
            system_time = System.currentTimeMillis();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onDestroy() {
        BaseApplication.getInstance().cancelPendingRequests(this.getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
