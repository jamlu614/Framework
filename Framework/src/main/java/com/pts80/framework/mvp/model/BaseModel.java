package com.pts80.framework.mvp.model;

import android.content.Context;

import com.pts80.framework.base.BaseApplication;
import com.pts80.framework.utils.ToastUtils;

/**
 * Author ljb
 * Created at 2016/10/24.
 * Description
 * model基类
 */

public abstract class BaseModel {

    public Context getContext() {
        return context;
    }

    protected Context context;

    public BaseModel(Context context) {
        this.context = context;
    }

    public static abstract class  Callback<D> {
        /**
         * 返回页面数据
         *
         * @param data 返回的数据
         */
       public abstract void onSuccess(D data);

        /**
         * 返回错误回调
         * @param msg
         */
       public void onFailure(String msg){
           ToastUtils.show(BaseApplication.getContext(),msg);
       }

    }
}
