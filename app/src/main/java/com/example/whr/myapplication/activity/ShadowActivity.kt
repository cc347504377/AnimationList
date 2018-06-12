package com.example.whr.myapplication.activity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import com.example.whr.myapplication.PublicUtils
import com.example.whr.myapplication.R
import com.example.whr.myapplication.util.BaseAdapter
import com.example.whr.myapplication.util.OnRefreshListener
import kotlinx.android.synthetic.main.activity_testctivity.*

class ShadowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testctivity)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        val adapter = BaseAdapter<String>({ parent, viewType ->
            val view = BaseAdapter.getView(parent, this, R.layout.item_transition)
            MyViewHoloder(view)
        }, { data, holder, position ->
            val viewHolder = holder as MyViewHoloder
            viewHolder.view.setOnClickListener { it ->
                val view = it as ViewGroup
                //阴影动画
                val animation = ObjectAnimator()
                if (view.translationZ > 0.0) {
                    animation.setFloatValues(20f, 0f)
                } else {
                    animation.setFloatValues(0.0f, 20f)
                }
                animation.duration = 500
                animation.interpolator = DecelerateInterpolator()
                animation.addUpdateListener {
                    view.translationZ = it.animatedValue as Float
                }
                animation.start()
            }
        })
        val refreshListener = object : OnRefreshListener<String> {
            override fun onRefresh(operation: (MutableList<String>) -> Unit) {
                operation(mutableListOf("s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"))
            }

            override fun onLoad(currentPage: Int, operation: (MutableList<String>) -> Unit) {
            }
        }
        rv_test.initRecyclerView(LinearLayoutManager(this), adapter, refreshListener)
        rv_test.refresh()
    }

    inner class MyViewHoloder(val view: View) : RecyclerView.ViewHolder(view)
}
