package com.example.whr.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.whr.myapplication.activity.BezierActivity;
import com.example.whr.myapplication.activity.CircleBezierActivity;
import com.example.whr.myapplication.activity.Main2Activity;
import com.example.whr.myapplication.activity.PathEffectActivity;
import com.example.whr.myapplication.activity.SceneActivity;
import com.example.whr.myapplication.activity.SharedElementActivity;
import com.example.whr.myapplication.activity.ShadowActivity;
import com.example.whr.myapplication.activity.TransitionActivity;
import com.example.whr.myapplication.activity.VectorActivity;
import com.example.whr.myapplication.activity.WavyActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initItem();
    }

    private void initItem() {
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String activity = mList.get(position);
        try {
            Intent intent = new Intent(this, Class.forName(activity));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(PathEffectActivity.class.getName());
        mList.add(BezierActivity.class.getName());
        mList.add(WavyActivity.class.getName());
        mList.add(CircleBezierActivity.class.getName());
        mList.add(TransitionActivity.class.getName());
        mList.add(SceneActivity.class.getName());
        mList.add(VectorActivity.class.getName());
        mList.add(SharedElementActivity.class.getName());
        mList.add(ShadowActivity.class.getName());
        mList.add(Main2Activity.class.getName());
    }
}
