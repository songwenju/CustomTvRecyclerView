package com.songwenju.customtvrecyclerview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.songwenju.customtvrecyclerview.R;


/**
 * @author admin
 */

public class CustomDialog extends BaseDialog {
    /**
     * dialog对应布局上的点击事件，该接口进行回调
     */
    private OnCallResult mOnCallResult;
    private FocusVerticalLinearLayout mOkBtn;
    private FocusVerticalLinearLayout mCancelBtn;
    private Drawable mBackDrawable;

    public CustomDialog(Context context) {
        super(context);
        initListener();
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        initListener();
    }

    private void initListener() {
        mOkBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    /**
     * 设置事件的回调接口
     */
    public void setCallBackListen(OnCallResult onCallResult) {
        mOnCallResult = onCallResult;
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_layout);

        mOkBtn = (FocusVerticalLinearLayout) findViewById(R.id.layout_video_call);
        mCancelBtn = (FocusVerticalLinearLayout) findViewById(R.id.layout_video_call2);
        mOkBtn.requestFocus();
    }

    @Override
    public void onDialogClick(View v) {
        if (mOnCallResult != null) {
            switch (v.getId()) {
                case R.id.layout_video_call:
                    mOnCallResult.onOkBtnClick();
                    break;
                case R.id.layout_video_call2:
                    mOnCallResult.onCancelBtnClick();
                default:
                    break;
            }
        }
    }
    public void setBackground(Drawable backBlurDrawable) {
        mBackDrawable = backBlurDrawable;
    }

    @Override
    public void show() {
        super.show();
        View view = findViewById(R.id.layout_dialog);
        view.setBackground(mBackDrawable);
    }

    /**
     *
     */
    public interface OnCallResult {

        /**
         * ok点击
         */
        void onOkBtnClick();

        /**
         * cancel点击
         */
        void onCancelBtnClick();
    }
}