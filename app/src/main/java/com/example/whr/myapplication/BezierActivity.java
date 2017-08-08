package com.example.whr.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class BezierActivity extends AppCompatActivity {

    private ReView mReView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        FrameLayout contentView = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        mReView = new ReView(this);
        mReView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.addView(mReView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("点我呀");
        return true;
    }

    private boolean mBoolean = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mBoolean = !mBoolean) {
            mReView.animatorIn();
        } else {
            mReView.animatorOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
