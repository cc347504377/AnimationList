package com.example.whr.myapplication.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.whr.myapplication.R
import kotlinx.android.synthetic.main.activity_shared_element2.*

class SharedElementActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_element2)
        fab_shared2.setOnClickListener {
            val snackBar = Snackbar.make(cl_snake, "here", Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("ok") {
                snackBar.dismiss()
            }
            snackBar.show()
        }
    }
}
