package com.example.whr.myapplication.activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.whr.myapplication.R
import kotlinx.android.synthetic.main.activity_shared_element.*
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import java.util.*

class SharedElementActivity : BaseSwipeBackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_element)
        val list = ArrayList<String>()
        for (i in 0..100) {
            list.add("这是第${i}项")
        }
        val myAdapter = MyAdapter(list)
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN        //允许上下的拖动
                val swipeFlags = ItemTouchHelper.LEFT   //只允许从右向左侧滑
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.layoutPosition
                val toPosition = target.layoutPosition
                Collections.swap(list, fromPosition, toPosition)
                myAdapter.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewHolder.layoutPosition.let {
                    list.removeAt(it)
                    myAdapter.notifyItemRemoved(it)
                }
            }

        })
        itemTouchHelper.attachToRecyclerView(rv_shared.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SharedElementActivity)
            adapter = myAdapter
            addItemDecoration(Divider())
        })

    }

    inner class MyAdapter(val data: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val inflater = LayoutInflater.from(this@SharedElementActivity)

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val view = inflater.inflate(R.layout.item_shared, parent, false)
            return MyHolder(view)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            val viewHolder = holder as MyHolder
            viewHolder.tv.text = data[position]
            viewHolder.view.setOnClickListener {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                        this@SharedElementActivity, it, "iv")
                val intent = Intent(this@SharedElementActivity, SharedElementActivity2::class.java)
                startActivity(intent, options.toBundle())
            }
        }

        inner class MyHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val tv = view.findViewById<TextView>(R.id.tv_shared_item)!!
            val iv = view.findViewById<ImageView>(R.id.iv_shared_item)!!
        }
    }

    inner class Divider : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            outRect.set(0, 0, 0, 1)
        }
    }
}
