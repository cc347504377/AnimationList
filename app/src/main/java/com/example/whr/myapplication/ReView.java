package com.example.whr.myapplication;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by whr on 8/7/17.
 */

public class ReView extends FrameLayout {

    private final RecyclerView mRecyclerView;
    private final BezierCurve mBezierCurve;
    private Handler mHandler = new Handler();

    public ReView(@NonNull Context context) {
        super(context);
        int maxHeight = MyApplication.mHeightPixels / 2;
        mBezierCurve = new BezierCurve(context);
        mBezierCurve.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBezierCurve.setMaxRectHeight(maxHeight);
        addView(mBezierCurve);

        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new MyAdapter());
        mRecyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight, Gravity.BOTTOM));
        mRecyclerView.setVisibility(INVISIBLE);
        addView(mRecyclerView);
    }

    public void animatorIn() {
        //shape
        mBezierCurve.open();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setVisibility(VISIBLE);
                //animation
                mRecyclerView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.review_item_in));
                //layoutAnimation
                LayoutAnimationController layoutAnimationController = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), R.anim.review_layout_in));
                mRecyclerView.setLayoutAnimation(layoutAnimationController);
                mRecyclerView.startLayoutAnimation();
            }
        }, 200);
    }

    public void animatorOut() {
        //shape
        mBezierCurve.close();
        mRecyclerView.setVisibility(INVISIBLE);
        //animation
        mRecyclerView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.review_item_out));
        //layoutAnimation
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), R.anim.review_layout_out));
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        mRecyclerView.startLayoutAnimation();
    }

    private class MyAdapter extends RecyclerView.Adapter {

        private final LayoutInflater mInflater;

        public MyAdapter() {
            mInflater = LayoutInflater.from(getContext());
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTv.setText("Hello World");
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mTv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
