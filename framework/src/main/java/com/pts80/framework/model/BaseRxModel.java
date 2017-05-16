package com.pts80.framework.model;

import android.content.Context;

import com.pts80.framework.base.BaseApplication;

/**
 * Author ljb
 * Created at 2016/10/24.
 * Description
 * model基类
 */

public abstract class BaseRxModel {

    public Context getContext() {
        return BaseApplication.getContext();
    }

    public BaseRxModel() {
    }
}
