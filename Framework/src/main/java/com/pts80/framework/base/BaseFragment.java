package com.pts80.framework.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pts80.framework.R;
import com.pts80.framework.net.volley.JsonRequest;
import com.pts80.framework.utils.ToastUtils;
import com.pts80.framework.utils.VolleyErrorHelper;
import com.pts80.framework.widget.MyProgressDialog;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment implements JsonRequest.OnCompleteListener {
    public ImageLoader mImageLoader = ImageLoader.getInstance();

    // 加载对话框
    public Dialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), setLayoutResID(), null);
        initViews(view);
        initDatas();
        initEvent();
        mProgressDialog = createProgressDialog();
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

//	public void setDialogText(String message) {
//		if (mProgressDialog != null
//				&& mProgressDialog instanceof MyProgressDialog) {
//			((MyProgressDialog) mProgressDialog).setTipText(message);
//		}
//	}

    /**
     * 加载数据开始,判断是否启用加载提示框
     */
    @Override
    public void onGetDataStart(boolean ishow) {
        if (ishow && mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    /**
     * 加载数据结束,判断是否关闭加载提示框
     */
    @Override
    public void onGetDataStop(boolean ishow) {
        if (ishow && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        // 新的activity创建了对话框如果旧的activity的对话框不取消很容易报错崩溃
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void onErrorResponse(VolleyError error, int which) {
        ToastUtils.show(getActivity(), VolleyErrorHelper.getMessage(error, getActivity()));
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
//        mImageLoader.clearMemoryCache(); // 回收该页面缓存在内存的图片
        super.onDestroy();
    }

}
