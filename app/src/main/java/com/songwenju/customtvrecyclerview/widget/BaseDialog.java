package com.songwenju.customtvrecyclerview.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * @author songwj3
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    public Context mContext;

    BaseDialog(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    /**
     * init view
     */
    public abstract void initView();

    /**
     * onDialog click
     *
     * @param v
     */
    public abstract void onDialogClick(View v);

    /**
     * 查找子控件，省强转
     */
    <T> T findView(int id) {
        T view = (T) findViewById(id);
        return view;
    }

    @Override
    public void onClick(View v) {           //点击事件
        onDialogClick(v);
    }
}
