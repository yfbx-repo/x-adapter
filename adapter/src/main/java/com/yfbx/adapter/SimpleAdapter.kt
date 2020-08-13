package com.yfbx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2020-08-12
 * Description:
 */

/**
 * 单布局
 */
inline fun <reified T> RecyclerView.bind(layoutId: Int, data: List<T>, noinline binder: (helper: BaseViewHolder, item: T) -> Unit): BaseAdapter<T> {
    val mAdapter = adapter(layoutId, binder)
    mAdapter.addAll(data)
    adapter = mAdapter
    return mAdapter
}

fun <T> adapter(layoutId: Int, data: List<T>, binder: (helper: BaseViewHolder, item: T) -> Unit): BaseAdapter<T> {
    val mAdapter = adapter(layoutId, binder)
    mAdapter.addAll(data)
    return mAdapter
}

fun <T> adapter(layoutId: Int, binder: (helper: BaseViewHolder, item: T) -> Unit): BaseAdapter<T> {
    return object : BaseAdapter<T>() {
        override fun onBind(holder: BaseViewHolder, item: T) {
            binder.invoke(holder, item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        }
    }
}
