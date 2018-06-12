package com.example.whr.myapplication.util

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by whr on 11/2/17.
 * RecyclerView 适配器
 */
class BaseAdapter<T>(
        /**
         * 传入createViewHolder的代码块，需要返回对应的ViewHolder对象
         */
        private inline val createViewHolder: (parent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder,
        /**
         * 传入bindView方法的代码块，包含对view具体的数据填充等操作
         */
        private inline val bindView: (data: MutableList<T>,
                                      holder: RecyclerView.ViewHolder,
                                      position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * 数据集合，默认为List，类型为Adapter泛型为准
     */
    val data: MutableList<T> = ArrayList()

    /**
     * 对外提供简易的view解析方法
     */
    companion object {
        fun getView(parent: ViewGroup, context: Context, @LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, parent, false)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            createViewHolder.invoke(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindView.invoke(data, holder, position)
    }

    override fun getItemCount() = data.size

}