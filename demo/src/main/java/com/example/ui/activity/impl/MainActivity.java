package com.example.ui.activity.impl;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.example.factory.fragmentfactory.HomeFragmentFactory;
import com.example.factory.presenterfactory.MainPresenterFactory;
import com.example.presenter.MainPresenter;
import com.example.ui.activity.IMainView;
import com.example.ui.fragment.AbstractHomeFragment;
import com.framework.example.R;
import com.jamlu.framework.base.BaseRxActivity;
import com.jamlu.framework.base.Factory;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

/**
 * Created by ljb 2017/05/31
 * 主体页面
 */
public class MainActivity extends BaseRxActivity<MainPresenter> implements IMainView {


    @BindView(R.id.bb_view)
    BottomBar mBbView;
    private AbstractHomeFragment mHomeFragment;//首页
    private AbstractHomeFragment mCategoryFragment;//分类
    private AbstractHomeFragment mChangeFragment;//兑换
    private AbstractHomeFragment mShopCarFragment;//购物车
    private AbstractHomeFragment mMineFragment;//我的
    private Fragment mCurrentFragment;//当前片段
    private Fragment mShowFragment;//将要显示的片段

    @Override
    public int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenterFactory(this, this).create();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
    }

    @Override
    protected void setupTitleBar(ViewGroup rootView) {
        super.setupTitleBar(rootView);
    }

    @Override
    protected boolean isShowBackIcon() {
        return false;
    }

    @Override
    public void initData() {
        setTitle("mainActivity");
        mHomeFragment = new HomeFragmentFactory("homeFragment").create();
        mCurrentFragment = mHomeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mCurrentFragment, mCurrentFragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    @Override
    public void initEvent() {
        mBbView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home://首页
                        mHomeFragment = (AbstractHomeFragment) createFragment(mHomeFragment, new HomeFragmentFactory("homeFragment"));
                        mShowFragment = mHomeFragment;
                        break;
                    case R.id.tab_category://分类
                        mCategoryFragment = (AbstractHomeFragment) createFragment(mCategoryFragment, new HomeFragmentFactory("categoryFragment"));
                        mShowFragment = mCategoryFragment;
                        break;
                    case R.id.tab_change://兑换
                        mChangeFragment = (AbstractHomeFragment) createFragment(mChangeFragment, new HomeFragmentFactory("changeFragment"));
                        mShowFragment = mChangeFragment;
                        break;
                    case R.id.tab_shopcar://购物车
                        mShopCarFragment = (AbstractHomeFragment) createFragment(mShopCarFragment, new HomeFragmentFactory("shopCarFragment"));
                        mShowFragment = mShopCarFragment;
                        break;
                    case R.id.tab_mine://我的
                        mMineFragment = (AbstractHomeFragment) createFragment(mMineFragment, new HomeFragmentFactory("mineFragment"));
                        mShowFragment = mMineFragment;
                        break;
                    default:
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), mCurrentFragment, mShowFragment);
            }
        });
    }

    /**
     * 添加或显示片段
     *
     * @param transaction
     * @param currentFragment
     * @param showFragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment currentFragment, Fragment showFragment) {
        if (currentFragment == showFragment) {
            return;
        }

        //添加和显示碎片
        if (!showFragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.fl_content, showFragment, showFragment.getClass().getSimpleName()).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(showFragment).commitAllowingStateLoss();
        }
        if (currentFragment != null) {
            //设置碎片显示状态
            currentFragment.setUserVisibleHint(false);//设置用户不可见
            mCurrentFragment = showFragment;
            mCurrentFragment.setUserVisibleHint(true);//懒加载
        }
    }


    /**
     * 如果fragment不存在就创建
     *
     * @param createFragment
     * @return
     */
    private Object createFragment(Fragment createFragment, Factory factory) {
        if (createFragment == null) {
            return factory.create();
        }
        return createFragment;
    }
}
