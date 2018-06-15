package com.example.whr.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.whr.myapplication.view.MyView;
import com.example.whr.myapplication.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class PathEffectActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        FrameLayout rootView = (FrameLayout) findViewById(R.id.rootView);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.addView(new MyView(this));

        FrameLayout view = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        layoutParams.setMargins(0, 0, 0, 50);
        textView.setLayoutParams(layoutParams);
        textView.setText("Hello World ");
        view.addView(textView);
    }
}
