package com.songwenju.customtvrecyclerview;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;


public class PopLayoutAnimation {
    private static Animation transAnim, alphaAnim;
    static LayoutAnimationController controller;

    public static LayoutAnimationController orderAnimation() {
        LayoutAnimationController controller;
        Animation transAnim, alphaAnim;
        AnimationSet set = new AnimationSet(false);
        transAnim = new TranslateAnimation(0, 0, -50, 0);
        transAnim.setDuration(167);
        transAnim.setFillEnabled(true);
        transAnim.setFillAfter(true);

        alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setDuration(167);
        alphaAnim.setFillAfter(true);
        set.addAnimation(transAnim);
        set.addAnimation(alphaAnim);
        controller = new LayoutAnimationController(set, 1);
        controller.setDelay(0.33f);
        return controller;
    }

    public static LayoutAnimationController reserverAnimation(){
        AnimationSet set = new AnimationSet(false);
        transAnim = new TranslateAnimation(0, 0,50, 0);
        transAnim.setDuration(167);
        transAnim.setFillEnabled(true);
        transAnim.setFillAfter(true);
        // transAnim.setFillBefore(true);
        alphaAnim = new AlphaAnimation(0,1);
        alphaAnim.setDuration(167);
        alphaAnim.setFillAfter(true);
        set.addAnimation(transAnim);
        set.addAnimation(alphaAnim);
        controller = new LayoutAnimationController(set, 1);
        controller.setDelay(0.33f);
         controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
        return controller;
    }
    public static boolean isNullAnimation(){
        if (controller==null||controller.isDone()){
            return true;
        }
        else{
            return false;
        }
    }
    public static void resetAnimation(){
        controller.start();
    }
}
