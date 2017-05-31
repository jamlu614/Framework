package com.example.model;

import com.jamlu.framework.model.bean.BaseBean;

import rx.Observable;

/**
 * Created by ljb on 2017/5/25.
 */

public interface HomeModel {
    Observable<BaseBean> getSinaTouTiao();
}

