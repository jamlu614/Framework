package com.example.rxjava.frameworkdemo;

import com.jamlu.framework.model.bean.BaseBean;

import rx.Observable;

/**
 * Created by ljb on 2017/5/25.
 */

public interface TestModel {
    Observable<BaseBean> getSinaTouTiao();
}

