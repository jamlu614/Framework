package com.jamlu.framework.model;

import android.content.Context;

import com.jamlu.framework.base.BaseApplication;

/**
 * Author ljb
 * Created at 2016/10/24.
 * Description
 * model基类
 */

public abstract class AbstractModel {

    public Context getContext() {
        return BaseApplication.getContext();
    }

    public AbstractModel() {
    }
}
