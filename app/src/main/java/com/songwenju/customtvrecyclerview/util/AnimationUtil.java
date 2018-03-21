package com.songwenju.customtvrecyclerview.util;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author songwenju
 */

public class AnimationUtil {
    private static AnimationUtil animationUtil;
    private final static float SCALE = 1.1f;
    private final static int DURATION = 100;
    private int mMinSdkVersion = 21;

    public static AnimationUtil getInstance() {
        if (animationUtil == null) {
            animationUtil = new AnimationUtil();
        }
        return animationUtil;
    }

    /**
     * 放大动画效果
     *
     * @param itemView
     */
    public void zoomIn(View itemView, float scale) {
        if (itemView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= mMinSdkVersion) {
            //抬高Z轴
            ViewCompat.animate(itemView).scaleX(scale).scaleY(scale).translationZ(1).setDuration(DURATION).start();
        } else {
            ViewCompat.animate(itemView).scaleX(scale).scaleY(scale).setDuration(DURATION).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    public void zoomIn(View itemView) {
        if (itemView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= mMinSdkVersion) {
            //抬高Z轴
            ViewCompat.animate(itemView).scaleX(SCALE).scaleY(SCALE).translationZ(1).setDuration(DURATION).start();
        } else {
            ViewCompat.animate(itemView).scaleX(SCALE).scaleY(SCALE).setDuration(DURATION).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    /**
     * 缩小动画效果
     *
     * @param itemView
     */
    public void zoomOut(View itemView) {
        if (itemView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= mMinSdkVersion) {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).translationZ(0).setDuration(DURATION).start();
        } else {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).setDuration(DURATION).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    /**
     * Y轴平移
     */
    public void translationY(View view, int dy) {
        ViewCompat.animate(view).translationY(dy).setDuration(DURATION).start();
    }

    /**
     * Y轴平移
     */
    public void translation(View view, int dy, int z) {
        ViewCompat.animate(view).translationY(dy).translationZ(z).setDuration(DURATION).start();
    }

}
