package com.pts80.framework.mvp.model;

import android.content.Context;

import com.android.volley.VolleyError;
import com.pts80.framework.R;
import com.pts80.framework.net.volley.JsonRequest;
import com.pts80.framework.utils.ToastUtils;
import com.pts80.framework.utils.VolleyErrorHelper;
import com.pts80.framework.widget.MyProgressDialog;

/**
 * Author ljb
 * Created at 2016/10/24.
 * Description
 * model基类
 */

public abstract class BaseModelVolley implements JsonRequest.OnCompleteListener {

    private MyProgressDialog mMyProgressDialog; // 加载对话框

    public Context getContext() {
        return context;
    }

    protected Context context;

    public BaseModelVolley(Context context) {
        this.context = context;
        mMyProgressDialog = new MyProgressDialog(getContext(), R.style.ProgressDialogStyle);
    }

    /**
     * 显示加载对话框
     */
    protected void showProgressDialog(boolean isShowDialog) {
        if (isShowDialog && mMyProgressDialog != null) {
            mMyProgressDialog.show();
        }
    }

    /**
     * 隐藏加载对话框
     */
    protected void dismissProgressDialog(boolean isShowDialog) {
        if (isShowDialog) {
            if (mMyProgressDialog != null && mMyProgressDialog.isShowing()) {
                mMyProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onGetDataStart(boolean ishow) {
        showProgressDialog(ishow);
    }

    @Override
    public void onGetDataStop(boolean ishow) {
        dismissProgressDialog(ishow);
    }

    @Override
    public void onErrorResponse(VolleyError error, int which) {
        ToastUtils.show(getContext(), VolleyErrorHelper.getMessage(error, getContext()));
    }

    public interface Callback {
        /**
         * 返回页面数据失败，失败报文
         *
         */
        void onFailure(String msg);

    }

}
