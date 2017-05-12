package com.pts80.framework.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pts80.framework.R;
import com.pts80.framework.net.volley.JsonRequest;
import com.pts80.framework.utils.CloseAllActivities;
import com.pts80.framework.utils.ToastUtils;
import com.pts80.framework.utils.VolleyErrorHelper;
import com.pts80.framework.widget.MyProgressDialog;
import com.yanzhenjie.permission.AndPermission;

/**
 * Activity基类，需扩展，可根据项目做调整
 */
public abstract class BaseActivity extends AppCompatActivity implements JsonRequest.OnCompleteListener {
    public ImageLoader mImageLoader = ImageLoader.getInstance();

    // 加载对话框
    public Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseAllActivities.add(this);
        setContentView(setLayoutResID());
        initViews(savedInstanceState);
        mProgressDialog = createProgressDialog();
        initDatas();
        initEvent();
    }

    /**
     * 实现setContentView()
     */
    public abstract int setLayoutResID();

    /**
     * 初始化布局组件
     */
    public abstract void initViews(Bundle savedInstanceState);

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
        return new MyProgressDialog(this, R.style.ProgressDialogStyle);
    }

    /**
     * 设置activity主标题，在activity中调用此方法即可
     * @param title  标题文字
     */
	public void setTitleBar(String title) {
		TextView tvTitle = (TextView) findViewById(R.id.txt_title_title_bar);
		if (tvTitle != null) {
			tvTitle.setText(title);
		}
	}

    /**
     * 加载数据开始,判断是否启用加载提示框
     */
    @Override
    public void onGetDataStart(boolean ishow) {
        if (ishow && mProgressDialog != null) {
            Log.e("xu-mProgressDialog", "true");
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

    /**
     * 返回
     *
     * @param v
     */
    public void onBack(View v) {
        onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        CloseAllActivities.remove(this);
    }

    @Override
    protected void onStop() {
        // 新的activity创建了对话框,如果旧的activity的对话框不取消容易报错崩溃
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        super.onStop();
    }

    @Override
    public void onErrorResponse(VolleyError error, int which) {
        ToastUtils.show(this, VolleyErrorHelper.getMessage(error, this));
        // 取消当前页面所有请求
        BaseApplication.getInstance().cancelPendingRequests(this.getClass().getSimpleName());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求权限
     *
     * @param requestCode
     */
    protected void requestPermission(int requestCode, String permission) {
        AndPermission.with(this)
                .requestCode(requestCode)
                .permission(permission)
                .send();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        // 取消当前页面所有请求
        BaseApplication.getInstance().cancelPendingRequests(this.getClass().getSimpleName());
//        mImageLoader.clearMemoryCache(); // 回收该页面缓存在内存的图片
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
