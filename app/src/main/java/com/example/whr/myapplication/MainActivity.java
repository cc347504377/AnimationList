package com.example.whr.myapplication;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }
}
