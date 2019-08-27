package com.example.whr.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by whr on 8/7/17.
 */

public class MyApplication extends Application {

    public static int mWidthPixels;
    public static int mHeightPixels;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initDisplaySize();
        context = this;
    }

    private void initDisplaySize() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        mWidthPixels = metrics.widthPixels;
        mHeightPixels = metrics.heightPixels;
    }

}
