package com.jamlu.framework.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.jamlu.framework.R;


public class MyProgressDialog extends Dialog {

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_loading);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        setCancelable(false);
    }

}
