package com.songwenju.customtvrecyclerview.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.songwenju.customtvrecyclerview.Common.TvRecyclerViewApplication;

/**
 * sharedPreferences的管理类
 * 作者：songwenju on 2016/1/31 10:34
 * 邮箱：songwenju01@163.com
 */
public class SpUtil {
    private static SharedPreferences mSp;
    private static final Context mContext = TvRecyclerViewApplication.mContext;

    /**
     * 校验sharedPreferences
     *
     * @return
     */
    private static SharedPreferences checkoutSP() {
        if (mSp == null) {
            mSp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSp;
    }

    /**
     * 使用sharedPreferences存入String类型的数据
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        mSp = checkoutSP();
        mSp.edit().putString(key, value).apply();
    }

    /**
     * 使用sharedPreferences存入String类型的数据,并设置默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue) {
        mSp = checkoutSP();
        return mSp.getString(key, defaultValue);
    }

    /**
     * 使用sharedPreferences存入boolean类型的数据
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        mSp = checkoutSP();
        mSp.edit().putBoolean(key, value).apply();
    }

    /**
     * 使用sharedPreferences存入boolean类型的数据,并设置默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        mSp = checkoutSP();
        return mSp.getBoolean(key, defaultValue);
    }

    /**
     * 使用sharedPreferences存入int类型的数据
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        mSp = checkoutSP();
        mSp.edit().putInt(key, value).apply();
    }

    /**
     * 使用sharedPreferences存入int类型的数据,并设置默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        mSp = checkoutSP();
        return mSp.getInt(key, defaultValue);
    }
}
