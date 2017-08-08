package com.example.whr.myapplication;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

/**
 * Created by whr on 8/8/17.
 */

public class WavyView extends View {

    private float radian;
    private int mWavyHeight;
    private final Paint mPaint;
    private int trans;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setTranslationX(-trans % MyApplication.mWidthPixels);
        }
    };

    public WavyView(Context context) {
        super(context);
        mPaint = new Paint();
        int color = ContextCompat.getColor(context, R.color.colorSkyBlue);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setShadowLayer(100, 0, 30, color);
        radian = PublicUtils.dip2px(30);
        trans = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mWavyHeight = getMeasuredHeight() / 2;
        Path path = new Path();
        path.moveTo(0, mWavyHeight);
        path.quadTo(MyApplication.mWidthPixels / 4, mWavyHeight - radian, MyApplication.mWidthPixels / 2, mWavyHeight);
        path.quadTo(MyApplication.mWidthPixels / 4 * 3, mWavyHeight + radian, MyApplication.mWidthPixels, mWavyHeight);
        path.quadTo(MyApplication.mWidthPixels / 4 * 5, mWavyHeight - radian, MyApplication.mWidthPixels / 2 * 3, mWavyHeight);
        path.quadTo(MyApplication.mWidthPixels / 4 * 7, mWavyHeight + radian, MyApplication.mWidthPixels * 2, mWavyHeight);
        path.lineTo(MyApplication.mWidthPixels * 2, getMeasuredHeight());
        path.lineTo(0, getMeasuredHeight());
        canvas.drawPath(path, mPaint);
    }

    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    trans +=10;
                    mHandler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.i("TAG", "InterruptedException: ");
                    }
                }
            }
        }).start();
    }
}
