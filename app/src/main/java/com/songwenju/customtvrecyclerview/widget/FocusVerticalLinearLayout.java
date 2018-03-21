package com.songwenju.customtvrecyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.songwenju.customtvrecyclerview.util.AnimationUtil;

/**
 * @author songwenju
 */

public class FocusVerticalLinearLayout extends LinearLayout implements View.OnHoverListener {

    public FocusVerticalLinearLayout(Context context) {
        this(context, null);
    }

    public FocusVerticalLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusVerticalLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    protected void init(Context context, AttributeSet attrs, int defStyle) {
        this.setClickable(true);
        this.setFocusable(true);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        setHovered(gainFocus);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        super.onHoverChanged(hovered);
        changeSelectedState(hovered);
    }

    @Override
    public boolean onHover(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_HOVER_ENTER:
                setHovered(true);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                setHovered(false);
                break;
            default:
                break;
        }
        return false;
    }

    void changeSelectedState(boolean selected) {
        if (selected) {
            AnimationUtil.getInstance().zoomIn(this);
        } else {
            AnimationUtil.getInstance().zoomOut(this);
        }

    }

}
