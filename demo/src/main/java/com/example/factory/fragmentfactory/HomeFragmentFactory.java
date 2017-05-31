package com.example.factory.fragmentfactory;

import android.os.Bundle;

import com.jamlu.framework.base.Factory;
import com.example.ui.fragment.AbstractHomeFragment;
import com.example.ui.fragment.impl.HomeFragmentImpl;

/**
 * Created by ljb on 2017/5/31.
 * 首页片段工厂
 */

public class HomeFragmentFactory implements Factory<AbstractHomeFragment> {
    private String content;

    public HomeFragmentFactory(String content) {
        this.content = content;
    }

    @Override
    public AbstractHomeFragment create() {
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        HomeFragmentImpl homeFragment = new HomeFragmentImpl();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }
}
