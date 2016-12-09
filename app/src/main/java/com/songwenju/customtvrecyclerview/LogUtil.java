package com.songwenju.customtvrecyclerview;

import android.util.Log;

/**
 * Log工具类
 */
public class LogUtil {
    //控制项目是否显示log
    private static boolean isShowLog = true;
    private static String sign = "swj";

    public static void i(String tag, String msg){
        if (isShowLog){
            Log.i(sign +tag,msg);
        }
    }

    public static void i(Object tag, String msg){
        if (isShowLog){
            Log.i(sign +tag.getClass().getSimpleName(),msg);
        }
    }
    public static void d(String tag, String msg){
        if (isShowLog){
            Log.d(sign +tag, msg);
        }
    }

    public static void d(Object tag, String msg){
        if (isShowLog){
            Log.d(sign +tag.getClass().getSimpleName(), msg);
        }
    }
    public static void w(String tag, String msg){
        if (isShowLog){
            Log.w(sign +tag, msg);
        }
    }

    public static void w(Object tag, String msg){
        if (isShowLog){
            Log.w(sign +tag.getClass().getSimpleName(), msg);
        }
    }
    public static void e(String tag, String msg){
        if (isShowLog){
            Log.e(sign +tag, msg);
        }
    }

    public static void e(Object tag, String msg){
        if (isShowLog){
            Log.e(sign +tag.getClass().getSimpleName(), msg);
        }
    }
}
