package com.songwenju.customtvrecyclerview.Common;

import android.app.Application;
import android.content.Context;


public class TvRecyclerViewApplication extends Application{
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
