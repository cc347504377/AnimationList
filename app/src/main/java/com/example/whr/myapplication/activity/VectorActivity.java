package com.example.whr.myapplication.activity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.whr.myapplication.R;

public class VectorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mVectorView;
    private AnimatedVectorDrawable mDrawableIn;
    private AnimatedVectorDrawable mDrawableOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        mVectorView = (ImageView) findViewById(R.id.iv_vector);
        mDrawableIn = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.vector_anim_in, null);
        mDrawableOut = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.vector_anim_out, null);
        mVectorView.setOnClickListener(this);
    }

    private boolean stat = true;
    @Override
    public void onClick(View v) {
        if (stat) {
            mVectorView.setImageDrawable(mDrawableIn);
            mDrawableIn.start();
        } else {
            mVectorView.setImageDrawable(mDrawableOut);
            mDrawableOut.start();
        }
        stat = !stat;
    }
}
