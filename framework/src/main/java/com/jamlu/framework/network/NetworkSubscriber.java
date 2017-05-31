package com.jamlu.framework.network;

import android.content.Context;
import android.support.annotation.UiThread;

import com.jamlu.framework.R;
import com.jamlu.framework.model.bean.BaseBean;
import com.jamlu.framework.ui.widget.MyProgressDialog;
import com.jamlu.framework.utils.ToastUtils;

import rx.Subscriber;

/**
 * Created by ljb on 2017/3/13.
 * 网络请求订阅器
 */

public class NetworkSubscriber<T extends BaseBean> extends Subscriber<T> {
    public static final int TOKEN_EXPIRED = 303;//token过期
    public static final int RESPOND_SUCCESS = 1;//响应成功
    public static final int RESPOND_FAILURE = 2;//响应失败
    public static final int RESPOND_NO_NETWORK = 0;//没有网络
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
            if (com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                mCallback.onFailure(RESPOND_FAILURE, "服务器访问超时");
            } else {
                mCallback.onFailure(RESPOND_NO_NETWORK, "连接网络超时，请重试");
            }
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        int httpCode = t.getHttpCode();
        if (httpCode == RESPOND_SUCCESS) {//返回成功回调
            if (mCallback != null) {
                mCallback.onSuccess(t);
            }
        } else if (httpCode == TOKEN_EXPIRED) {//token过期
            //清空token
//            PreferencesUtils.putString(BaseApplication.getContext(), PrefUtils.TOKEN, "");
            ToastUtils.show(t.getMsg());
            if (mCallback != null) {//响应错误回调
                mCallback.onFailure(RESPOND_FAILURE, t.getMsg());
            }
        } else {
            if (mCallback != null) {//响应错误回调
                mCallback.onFailure(RESPOND_FAILURE, t.getMsg());
            }
        }
    }

    /**
     * 显示加载对话框
     */
    @UiThread
    protected void showProgressDialog() {
        if (mIsShowDialog) {
            mMyProgressDialog = new MyProgressDialog(getContext(), R.style.ProgressDialogStyle);
            mMyProgressDialog.show();
        }
    }

    /**
     * 隐藏加载对话框
     */
    @UiThread
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
    public static abstract class Callback<T> {
        /**
         * 成功回调
         *
         * @param t
         */
        public abstract void onSuccess(T t);

        /**
         * 出错回调
         *
         * @param httpCode 请求码
         * @param msg      错误信息
         */
        public void onFailure(int httpCode, String msg) {
            ToastUtils.show(msg);
        }

        ;
    }

}
