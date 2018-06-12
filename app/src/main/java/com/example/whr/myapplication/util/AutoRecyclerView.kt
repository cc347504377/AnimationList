package com.example.whr.myapplication.util

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * Created by whr on 11/2/17.
 *
 */
interface OnRefreshListener<T> {
    fun onRefresh(operation: (MutableList<T>) -> Unit)

    fun onLoad(currentPage: Int, operation: (MutableList<T>) -> Unit)
}

/**
 * 封装了上拉加载更多监听
 * 封装了数据填充逻辑，使用者仅需要在initRecyclerView方法中传入对应的参数，便能在适当的时候自动进行数据请求及填充
 * 没有封装下拉刷新的监听，需要外部手动调用refresh方法
 */
class AutoRecyclerView : RecyclerView {

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private var currentPage = 1
    private var loadListener: ((currentPage: Int) -> Unit)? = null
    private var refreshListener: OnRefreshListener<Any>? = null
    private var mAdapter: BaseAdapter<Any>? = null

    init {
        itemAnimator = null
    }

    /**
     * 让item获得焦点
     */
    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        super.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (childCount > 0) {
                    getChildAt(0).requestFocus()
                }
            }
        }
    }

    /**
     * @param manager 可能会有别的用处,所以放在外部实现
     * @param refreshListener 此处需要传入刷新和加载所需的数据，可以直接传入对应网络请求的方法
     */

    fun <T> initRecyclerView(manager: LinearLayoutManager, adapter: BaseAdapter<T>, refreshListener: OnRefreshListener<T>) {
        overScrollMode = View.OVER_SCROLL_NEVER
        layoutManager = manager
        //设置adapter
        this.adapter = adapter
        //保存引用，因为View类不能为泛型类，所以在内部存为Any类型
        this.refreshListener = refreshListener as OnRefreshListener<Any>
        //保存引用
        this.mAdapter = adapter as BaseAdapter<Any>
        //添加监听
        initOnLoadListener()
    }

    /**
     * 初始化上拉加载的监听事件
     */
    private fun initOnLoadListener() {
        addOnScrollListener(EndlessRecyclerOnScrollListener())
        loadListener = { load() }
    }

    /**
     * 刷新数据，请求外部的数据刷新接口，并对数据进行填充
     */
    fun refresh() {
        refreshListener?.onRefresh { data ->
            currentPage = 2
            mAdapter?.let {
                val size = data.size
                it.data.clear()
                if (size > 0) {
                    currentPage += size
                    it.data.addAll(data)
                }
                it.notifyDataSetChanged()
            }
        }
    }

    /**
     * 加载数据,请求外部的数据加载接口，并对数据进行填充
     */
    private fun load() {
        refreshListener?.onLoad(currentPage) { data ->
            currentPage++
            mAdapter?.let {
                val aData = it.data
                val size = data.size
                val lastNum = aData.size
                if (size > 0) {
                    aData.addAll(data)
                    it.notifyItemRangeInserted(lastNum,size)
                }
            }
        }
    }

    private inner class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

        private var previousTotal = 0
        private var loading = true
        private var totalTime: Long = 0
        //加载次数(页数)
        private var currentPage = 1

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val visibleItemCount = recyclerView!!.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            //保证每次数据修改后只会刷新执行一次
            if (loading) {
                if (totalItemCount != previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading //已经执行过之前的判断
                    && totalItemCount - visibleItemCount <= firstVisibleItem  //判断当滑到底部
//tv端不能使用                    && newState == 1                                            //判断到底后再次滑动
                    && System.currentTimeMillis() - totalTime > 1000            //每次执行最小间隔为1s
            ) {
                totalTime = System.currentTimeMillis()
                currentPage++
                loading = true
                loadListener?.invoke(currentPage)
            }
        }
    }
}