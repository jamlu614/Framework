package com.example.rxjava.frameworkdemo.network;

import android.content.Context;

import com.example.rxjava.frameworkdemo.R;
import com.pts80.framework.base.BaseApplication;
import com.pts80.framework.mvp.bean.BaseBean;
import com.pts80.framework.utils.ToastUtils;
import com.pts80.framework.widget.MyProgressDialog;

import rx.Subscriber;

/**
 * Created by ljb on 2017/3/13.
 * 网络请求订阅器
 */

public class NetworkSubscriber<T extends BaseBean> extends Subscriber<T> {
    public static final int TOKEN_EXPIRED = 303;//token过期
    public static final int RESPOND_SUCCESS =0;//响应成功
    public static final int RESPOND_FAILURE = 1;//响应失败
    private MyProgressDialog mMyProgressDialog;
    protected Context context;
    //回调接口
    private Callback<T> mCallback;
    private boolean mIsShowDialog = true;//是否显示加载对话框

    public NetworkSubscriber(Context context, Callback<T> callback) {
        this.context = context;
        mCallback = callback;
    }
    public NetworkSubscriber(Context context, boolean isShowDialog, Callback<T> callback) {
        this.context = context;
        mCallback = callback;
        mIsShowDialog = isShowDialog;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mCallback != null) {
            mCallback.onFailure("服务器访问超时");
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        int httpCode = t.getErrno();
        if (httpCode == RESPOND_SUCCESS) {//返回成功回调
            if (mCallback != null) {
                mCallback.onSuccess(t);
            }
        } else if (httpCode == TOKEN_EXPIRED) {//token过期
            //清空token
//            PreferencesUtils.putString(BaseApplication.getContext(), PrefUtils.TOKEN, "");
            ToastUtils.show(BaseApplication.getContext(), "登录超时");
        } else {
            if (mCallback != null) {//响应错误回调
                mCallback.onFailure(t.getError());
            }
        }
    }

    /**
     * 显示加载对话框
     */
    protected void showProgressDialog() {
        if (mIsShowDialog) {
            mMyProgressDialog = new MyProgressDialog(getContext(), R.style.ProgressDialogStyle);
            mMyProgressDialog.show();
        }
    }

    /**
     * 隐藏加载对话框
     */
    protected void dismissProgressDialog() {
        if (mIsShowDialog) {
            if (mMyProgressDialog != null && mMyProgressDialog.isShowing()) {
                mMyProgressDialog.dismiss();
            }
        }
    }
    /**
     * 接口回调结果
     */
    public interface Callback<T> {
        /**
         * 成功回调
         *
         * @param t
         */
       void onSuccess(T t);

        /**
         * 失败回调
         *
         * @param msg
         */
        void onFailure(String msg);
    }

}
