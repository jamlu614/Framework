package com.pts80.framework.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pts80.framework.R;
import com.pts80.framework.mvp.presenter.BasePresenter;
import com.pts80.framework.mvp.view.BaseIView;
import com.pts80.framework.utils.CloseAllActivities;
import com.yanzhenjie.permission.AndPermission;

/**
 * Activity基类，需扩展，可根据项目做调整
 */
public abstract class BaseMvpActivity<V extends BaseIView, T extends BasePresenter<V>> extends AppCompatActivity {
    public ImageLoader mImageLoader = ImageLoader.getInstance();
    private TextView mTxtTitle;
    private ImageView mImgLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseAllActivities.add(this);
        setContentView(setLayoutResID());
        setupTitleBar();
        presenter = createPresenter();
        presenter.attachView((V) this);
        initViews(savedInstanceState);
        initData();
        initEvent();
    }

    @NonNull
    public T getPresenter() {
        return presenter;
    }

    private T presenter;

    /**
     * 实现setContentView()
     */
    public abstract int setLayoutResID();

    /**
     * 初始化P层实现类
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 初始化布局组件
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化布局组件的数据
     */
    public abstract void initData();

    /**
     * 初始化布局组件的监听事件
     */
    public abstract void initEvent();

    /**
     * 初始化标题栏
     */
    private void setupTitleBar() {
        mTxtTitle = (TextView) getWindow().getDecorView().findViewById(R.id.txt_title_title_bar);
        mImgLeft = (ImageView) getWindow().getDecorView().findViewById(R.id.img_back_title_bar);
        if (mImgLeft != null && mImgLeft.getVisibility() == View.VISIBLE) {
            mImgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBack(v);
                }
            });
        }
    }

    protected void setTitle(@NonNull String title) {
        if (mTxtTitle != null) {
            mTxtTitle.setText(title);
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
    protected void onDestroy() {
        BaseApplication.getInstance().cancelPendingRequests(this.getClass().getSimpleName());
        mImageLoader.clearMemoryCache(); // 回收该页面缓存在内存的图片
        presenter.detachView((V) this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
