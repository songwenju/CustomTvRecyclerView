package com.songwenju.customtvrecyclerview.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

/**
 * @author admin
 */

public class UIUtil {
    private static final String TAG = "UIUtil";
    private static View view;

    /**
     * 获得背景的高斯模糊图 drawable
     *
     * @param context
     * @param radius
     * @return
     */
    public static Drawable getBackBlurDrawable(Context context, float radius) {
        return new BitmapDrawable(context.getResources(), getBackBlurBitmap(context, radius));
    }

    /**
     * 获取整个窗口的截图
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    private static Bitmap captureScreen(Activity context) {
        if (view != null){
            //清空缓冲
            view.destroyDrawingCache();
        }
        view = context.getWindow().getDecorView();

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }

        bmp.setHasAlpha(false);
        bmp.prepareToDraw();

        return bmp;
    }

    /**
     * 获得当前应用背景的高斯模糊的bitmap
     *
     * @param context
     * @param radius
     * @return
     */
    public static Bitmap getBackBlurBitmap(Context context, float radius) {
        Bitmap bitmap = captureScreen(((Activity) context));
        LogUtil.i(TAG,"UIUtil.getBackBlurBitmap:"+bitmap);
        return blur(context, bitmap, radius);
    }

    /**
     * 返回高斯模糊的图片效果
     *
     * @param context
     * @param bitmap
     * @param radius
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Context context, Bitmap bitmap, float radius) {
        // 创建输出图片
        Bitmap output = Bitmap.createBitmap(bitmap);
        // 构建一个RenderScript对象
        RenderScript rs = RenderScript.create(context);
        // 创建高斯模糊脚本
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 创建用于输入的脚本类型
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        // 创建用于输出的脚本类型
        Allocation allOut = Allocation.createFromBitmap(rs, output);
        // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setRadius(radius);
        // 设置输入脚本类型
        gaussianBlue.setInput(allIn);
        // 执行高斯模糊算法，并将结果填入输出脚本类型中
        gaussianBlue.forEach(allOut);
        // 将输出内存编码为Bitmap，图片大小必须注意
        allOut.copyTo(output);
        // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        rs.destroy();
        return output;
    }
}
