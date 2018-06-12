package com.example.whr.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.whr.myapplication.MyApplication;
import com.example.whr.myapplication.R;
import com.example.whr.myapplication.view.WavyView;

public class WavyActivity extends AppCompatActivity {

    private WavyView mWavyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wavy);
        mWavyView = (WavyView)findViewById(R.id.wavy);
    }

    private boolean stat = true;
    public void click(View view) {
        if (stat) {
            mWavyView.run();
        } else {
            mWavyView.stop();
        }
        stat = !stat;
    }
}
