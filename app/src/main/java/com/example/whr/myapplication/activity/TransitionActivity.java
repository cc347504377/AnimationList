package com.example.whr.myapplication.activity;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.whr.myapplication.PublicUtils;
import com.example.whr.myapplication.R;

public class TransitionActivity extends AppCompatActivity {

    private FrameLayout mAnimationView;
    private CardView mIView;
    private Point mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        mAnimationView = findViewById(R.id.fl_animationLayout);
        mIView = findViewById(R.id.iv_view);
        mPoint = new Point();
        getWindowManager().getDefaultDisplay().getSize(mPoint);
    }

    private boolean stat = false;

    public void click(View view) {
        TransitionManager.beginDelayedTransition(mAnimationView, new ChangeBounds());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIView.getLayoutParams();
        if (stat) {
            layoutParams.width = PublicUtils.dip2px(100);
            layoutParams.height = PublicUtils.dip2px(100);
            layoutParams.gravity = Gravity.CENTER;
            mIView.setCardElevation(PublicUtils.dip2px(5));
        } else {
            layoutParams.width = mPoint.x;
            layoutParams.height = mPoint.y / 3;
            layoutParams.gravity = Gravity.TOP;
            mIView.setCardElevation(PublicUtils.dip2px(10));
        }
        mIView.setLayoutParams(layoutParams);
        stat = !stat;
    }
}
