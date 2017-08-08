package com.example.whr.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class WavyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wavy);
        FrameLayout contentView = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        WavyView wavyView = new WavyView(this);
        wavyView.setLayoutParams(new FrameLayout.LayoutParams(MyApplication.mWidthPixels * 2, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.addView(wavyView);
        wavyView.run();
    }
}
