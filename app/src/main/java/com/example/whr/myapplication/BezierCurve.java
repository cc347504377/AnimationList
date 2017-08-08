package com.example.whr.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by whr on 8/7/17.
 */

public class BezierCurve extends View {

    private final Paint mPaint;
    private float rectHeight = 0;
    private float vertex = 0;
    private int maxVertex;
    private int maxRectHeight;

    public BezierCurve(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        maxVertex = PublicUtils.dip2px(65);
    }

    public void setMaxRectHeight(int height) {
        maxRectHeight = height;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        path.moveTo(0, getMeasuredHeight());
        path.lineTo(0, getMeasuredHeight() - rectHeight);
        path.quadTo(MyApplication.mWidthPixels / 2, getMeasuredHeight() - rectHeight - vertex, MyApplication.mWidthPixels, getMeasuredHeight() - rectHeight);
        path.lineTo(MyApplication.mWidthPixels, getMeasuredHeight());
        canvas.drawPath(path, mPaint);
    }

    public void open() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, maxVertex);
        valueAnimator.setDuration(350);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                vertex = (int) animation.getAnimatedValue();
                rectHeight = vertex / maxVertex * maxRectHeight;
                invalidate();
                if (vertex == maxVertex) {
                    stop();
                }
            }
        });
        valueAnimator.start();
    }

    private void stop() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(maxVertex, 0);
        valueAnimator.setDuration(150);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                vertex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void close() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(maxRectHeight, 0);
        valueAnimator.setDuration(500);
//        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rectHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

}
