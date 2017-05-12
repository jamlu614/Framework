package com.pts80.framework.mvp.model;

import android.content.Context;

/**
 * Author ljb
 * Created at 2016/10/24.
 * Description
 * model基类
 */

public abstract class BaseRxModel {

    public Context getContext() {
        return context;
    }

    protected Context context;

    public BaseRxModel(Context context) {
        this.context = context;
    }
}
