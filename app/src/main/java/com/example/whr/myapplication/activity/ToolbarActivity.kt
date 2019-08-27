package com.example.whr.myapplication.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.whr.myapplication.R
import kotlinx.android.synthetic.main.activity_main2.*

class ToolbarActivity : AppCompatActivity() {

    private var btnHide = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button.setOnClickListener {
            if (btnHide) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
            btnHide = !btnHide
        }
    }
}
