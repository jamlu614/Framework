package com.jamlu.framework.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jamlu.framework.R;
import com.jamlu.framework.presenter.BaseRxPresenter;
import com.jamlu.framework.utils.ActivityUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ljb on 2017/05/26
 * Activity基类，需扩展，可根据项目做调整
 */
public abstract class BaseRxActivity<T extends BaseRxPresenter> extends AppCompatActivity implements View.OnClickListener {
    private T presenter;
    private RxPermissions mRxPermissionManager;//动态权限管理器
    private int mBackIconRes = 0;//返回按钮图标
    protected Toolbar mToolbar;//标题栏
    private TextView mTvTitle;//标题控件
    private int mTitleRes = -1;//标题资源
    private View mTitleLayout;//标题布局

    //正常
    public final static int STATUS_SUCCESS = 1;
    //服务器出错
    public final static int STATUS_ERROR = 2;
    //没有网络
    public final static int STATUS_NO_NETWORK = 3;
    //当前状态,默认正常
    private int mCurrentStatus = STATUS_SUCCESS;
    //内容布局
    private ViewGroup mContentView;
    //根布局
    private ViewGroup mRootView;
    private Bundle mSavedInstanceState;
    private View mSuccessView;


    @IntDef({STATUS_SUCCESS, STATUS_ERROR, STATUS_NO_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageStatus {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        ActivityUtils.add(this);
        mRootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_layout, ((ViewGroup) getWindow().getDecorView()), false);
        presenter = createPresenter();
        mRxPermissionManager = new RxPermissions(this);
        //设置内容布局
        setContentView(mRootView);
        init(mRootView);
    }

    /**
     * 初始化界面
     *
     * @param rootView
     */
    private void init(ViewGroup rootView) {
        setupTitleBar(mRootView);
        //生成内容布局
        mContentView = (ViewGroup) rootView.findViewById(R.id.fl_content);
        //初始化内容布局
        initContentView(rootView);
    }

    /**
     * 初始化内容布局
     *
     * @param rootView
     */
    private void initContentView(final ViewGroup rootView) {
        final View contentView;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        switch (mCurrentStatus) {
            case STATUS_SUCCESS://正常
                if (mSuccessView == null) {
                    mSuccessView = LayoutInflater.from(this).inflate(setLayoutResID(), rootView, false);
                    mContentView.addView(mSuccessView);
                    ButterKnife.bind(this);
                    initViews(mSavedInstanceState);
                    initData();
                    initEvent();
                }
                break;
            case STATUS_NO_NETWORK://没有网络
                contentView = LayoutInflater.from(this).inflate(R.layout.status_no_network, rootView, false);
                boolean hasUseFragment = false;//是否有可用的fragment
                if (fragments != null && fragments.size() > 0) {
                    for (Fragment fragment : fragments) {
                        if (fragment != null && !fragment.isHidden() && fragment instanceof BaseRxFragment && fragment.getView() != null) {
                            ((ViewGroup) fragment.getView()).addView(contentView);
                            hasUseFragment = true;
                        }
                    }
                    if (!hasUseFragment) {
                        mContentView.addView(contentView);
                    }
                } else {
                    mContentView.addView(contentView);
                }
                View btnReload = rootView.findViewById(R.id.btn_reload);
                btnReload.setOnClickListener(BaseRxActivity.this);
                break;
            case STATUS_ERROR://服务器出错
                contentView = LayoutInflater.from(this).inflate(R.layout.status_error, rootView, false);
                boolean hasUseFragment2 = false;//是否有可用的fragment
                if (fragments != null && fragments.size() > 0) {
                    for (Fragment fragment : fragments) {
                        if (fragment != null && !fragment.isHidden() && fragment instanceof BaseRxFragment && fragment.getView() != null) {
                            ((ViewGroup) fragment.getView()).addView(contentView);
                            hasUseFragment2 = true;
                        }
                    }
                    if (!hasUseFragment2) {
                        mContentView.addView(contentView);
                    }
                } else {
                    mContentView.addView(contentView);
                }
                View btnReload2 = rootView.findViewById(R.id.btn_reload);
                btnReload2.setOnClickListener(BaseRxActivity.this);
                break;
            default:
                break;
        }
    }

    /**
     * 绘制页面
     *
     * @param status
     */
    public void loadView(@PageStatus int status) {
        if (mCurrentStatus != status) {
            mCurrentStatus = status;
            for (int i = 0; i < mContentView.getChildCount(); i++) {
                View childView = mContentView.getChildAt(i);
                if (!childView.equals(mSuccessView)) {//如果不是成功布局，删除布局
                    mContentView.removeView(childView);
                } else {
                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    if (fragments != null && fragments.size() > 0) {
                        for (Fragment fragment : fragments) {
                            if (fragment != null && fragment.getView() != null) {
                                for (int j = 0; j < ((ViewGroup) fragment.getView()).getChildCount(); j++) {
                                    View child = ((ViewGroup) fragment.getView()).getChildAt(j);
                                    if (child.getId() == R.id.fl_error || child.getId() == R.id.fl_noNetwork) {
                                        //如果是错误布局或者是没有网络布局,删除布局
                                        ((ViewGroup) fragment.getView()).removeView(child);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            initContentView(mRootView);
        }
    }

    /**
     * 实现setContentView()
     */
    @LayoutRes
    protected abstract int setLayoutResID();

    /**
     * 初始化标题栏
     *
     * @param rootView
     */
    protected void setupTitleBar(ViewGroup rootView) {
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
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
        ViewStubCompat titleSub = (ViewStubCompat) rootView.findViewById(R.id.title);
        if (mTitleRes == -1) {//默认标题布局
            titleSub.setLayoutResource(R.layout.title_textview);
            titleSub.inflate();
            mTvTitle = (TextView) findViewById(R.id.tv_title);
        } else {//自定义标题布局
            titleSub.setLayoutResource(mTitleRes);
            titleSub.inflate();
            mTvTitle = (TextView) findViewById(R.id.tv_title);
            mTitleLayout = findViewById(R.id.titleLayout);
        }
    }

    /**
     * 设置标题布局
     *
     * @param res
     */
    public void setTitleRes(@LayoutRes int res) {
        mTitleRes = res;
    }

    /**
     * 获取标题布局
     *
     * @return
     */
    public View getTitleLayout() {
        if (mTitleLayout != null) {
            return mTitleLayout;
        } else {
            return mTvTitle;
        }
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
    @AnyRes
    protected int setBackIcon() {
        return mBackIconRes;
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
        if (mTvTitle != null) {
            mTvTitle.setText(title);
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

    @Override
    public void onClick(View v) {
        //重新加载数据
        onReload();
    }


    /**
     * 如果要响应activity的重新加载按钮，请重写该方法
     */
    protected void onReload() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Observable.just(fragments).filter(new Func1<List<Fragment>, Boolean>() {
            @Override
            public Boolean call(List<Fragment> fragments) {
                return fragments != null && fragments.size() > 0;
            }
        }).flatMap(new Func1<List<Fragment>, Observable<Fragment>>() {
            @Override
            public Observable<Fragment> call(List<Fragment> fragments) {
                return Observable.from(fragments);
            }
        }).filter(new Func1<Fragment, Boolean>() {
            @Override
            public Boolean call(Fragment fragment) {
                //判断fragment是否可用
                return fragment != null && !fragment.isHidden() && fragment instanceof BaseRxFragment;
            }
        }).map(new Func1<Fragment, BaseRxFragment>() {
            @Override
            public BaseRxFragment call(Fragment fragment) {
                return ((BaseRxFragment) fragment);
            }
        }).subscribe(new Action1<BaseRxFragment>() {
            @Override
            public void call(BaseRxFragment baseRxFragment) {
                //回调fragment重新加载方法
                baseRxFragment.onReload();
            }
        });
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
}
