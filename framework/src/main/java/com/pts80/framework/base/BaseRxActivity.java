package com.pts80.framework.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pts80.framework.R;
import com.pts80.framework.presenter.inf.BaseRxPresenter;
import com.pts80.framework.ui.view.BaseIView;
import com.pts80.framework.utils.ActivityUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Activity基类，需扩展，可根据项目做调整
 */
public abstract class BaseRxActivity<V extends BaseIView, T extends BaseRxPresenter<V>> extends AppCompatActivity {
    private TextView mTxtTitle;
    private T presenter;
    private RxPermissions mRxPermissionManager;//动态权限管理器
    private int mBackIconRes = 0;//返回按钮图标
    private ViewGroup mRootView;//根布局
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
        mRootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_layout, ((ViewGroup) getWindow().getDecorView()), false);
        ViewGroup contentGroup = (ViewGroup) mRootView.findViewById(R.id.fl_content);
        View contentView = LayoutInflater.from(this).inflate(setLayoutResID(), mRootView, false);
        contentGroup.addView(contentView);
        setContentView(mRootView);
        setupTitleBar();
        presenter = createPresenter();
        mRxPermissionManager = new RxPermissions(this);
        initViews(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 实现setContentView()
     */
    protected abstract int setLayoutResID();

    /**
     * 初始化标题栏
     */
    private void setupTitleBar() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        //设置返回按钮图标
        if (mBackIconRes != 0) {
            mToolbar.setNavigationIcon(setBackIcon());
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(isShowBackIcon()); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBackIcon());
            if (isShowBackIcon()) {
                //点击返回键
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBack(v);
                    }
                });
            }
        }

        mTxtTitle = (TextView) mRootView.findViewById(R.id.tv_title);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 是否显示返回按钮
     *
     * @return
     */
    protected boolean isShowBackIcon() {
        return true;
    }


    /**
     * 设置返回按钮样式
     *
     * @return
     */
    private int setBackIcon() {
        return mBackIconRes;
    }

    /**
     * 初始化P层实现类
     *
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 初始化布局组件
     */
    protected void initViews(Bundle savedInstanceState) {
    }

    /**
     * 初始化布局组件的数据
     */
    protected abstract void initData();

    /**
     * 初始化布局组件的监听事件
     */
    protected void initEvent() {
    }

    private CompositeSubscription mCompositeSubscription; //这个类的内部是由Set<Subscription> 维护订阅者

    //添加已订阅的订阅器
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @NonNull
    protected T getPresenter() {
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

    @Override
    protected void onStart() {
        super.onStart();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //p层取消订阅
        presenter.unSubscribe();
        //v层取消订阅
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 返回
     *
     * @param v
     */
    protected void onBack(View v) {
        onBackPressed();
    }


    /**
     * 设置editText点击外部区域时可消失
     *
     * @param ev
     * @return
     */
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
     * 获取权限管理器
     *
     * @return
     */
    public RxPermissions getRxPermissionManager() {
        return mRxPermissionManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.titlebar_menu, menu);
        menu.findItem(R.id.item2).setVisible(false);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.remove(this);
    }

}
