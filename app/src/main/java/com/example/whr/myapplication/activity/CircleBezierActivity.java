package com.example.whr.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.whr.myapplication.view.CircleBezierView;
import com.example.whr.myapplication.R;

public class CircleBezierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_circle_bezier);
        CircleBezierView circleBezierView = new CircleBezierView(this);
        addContentView(circleBezierView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
    }
}
