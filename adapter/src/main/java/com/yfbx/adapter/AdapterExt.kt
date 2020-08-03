package com.yfbx.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */

/**
 * 单布局
 */
inline fun <reified T> RecyclerView.bind(layoutId: Int, data: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit): XAdapter {
    val xAdapter = adapter { bind(layoutId, data, binder) }
    adapter = xAdapter
    return xAdapter
}

/**
 * 多布局
 */
fun RecyclerView.bind(builder: XAdapter.() -> Unit): XAdapter {
    val xAdapter = adapter(builder)
    adapter = xAdapter
    return xAdapter
}

fun adapter(builder: XAdapter.() -> Unit): XAdapter {
    return XAdapter().apply(builder)
}