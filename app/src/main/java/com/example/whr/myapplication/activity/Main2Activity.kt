package com.example.whr.myapplication.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.whr.myapplication.R
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(my_toolbar)
        button.setOnClickListener {
            supportActionBar?.hide()
        }
    }
}
