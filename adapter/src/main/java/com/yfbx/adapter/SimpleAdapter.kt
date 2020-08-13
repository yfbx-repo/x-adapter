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
inline fun <reified T> RecyclerView.bind(layoutId: Int, data: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit): BaseAdapter<T, ViewHelper> {
    val mAdapter = adapter(layoutId, binder)
    mAdapter.addAll(data)
    adapter = mAdapter
    return mAdapter
}

fun <T> adapter(layoutId: Int, data: List<T>, binder: (helper: ViewHelper, item: T) -> Unit): BaseAdapter<T, ViewHelper> {
    val mAdapter = adapter(layoutId, binder)
    mAdapter.addAll(data)
    return mAdapter
}

fun <T> adapter(layoutId: Int, binder: (helper: ViewHelper, item: T) -> Unit): BaseAdapter<T, ViewHelper> {
    return object : BaseAdapter<T, ViewHelper>() {
        override fun onBind(holder: ViewHelper, item: T) {
            binder.invoke(holder, item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHelper {
            return ViewHelper(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        }
    }
}
