package com.example.whr.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.whr.myapplication.MyApplication;
import com.example.whr.myapplication.PublicUtils;
import com.example.whr.myapplication.R;

/**
 * Created by whr on 8/8/17.
 */

public class WavyView extends View {

    private float radian;
    private int mWavyHeight;
    private final Paint mPaint;
    private int trans;
    private Handler mHandler = new Handler();

    public WavyView(Context context) {
        this(context, null);
    }

    public WavyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public WavyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MyApplication.mWidthPixels*2,
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
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

    private boolean run = true;

    public void run() {
        run = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    trans += 10;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setTranslationX(-trans % MyApplication.mWidthPixels);
                        }
                    });
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

    public void stop() {
        run = false;
    }
}
