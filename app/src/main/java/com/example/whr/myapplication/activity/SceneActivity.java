package com.example.whr.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.whr.myapplication.R;

public class SceneActivity extends AppCompatActivity {

    private Scene mScene;
    private Scene mScene1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        LinearLayout rootView = (LinearLayout) findViewById(R.id.scene_root);
        mScene = Scene.getSceneForLayout(rootView, R.layout.activity_scene, this);
        mScene1 = Scene.getSceneForLayout(rootView, R.layout.activity_scene2, this);
    }

    private boolean stat = true;

    public void click(View view) {
        if (stat) {
            TransitionManager.go(mScene1, new ChangeBounds());
        } else {
            TransitionManager.go(mScene, new ChangeBounds());
        }
        stat = !stat;
    }
}
