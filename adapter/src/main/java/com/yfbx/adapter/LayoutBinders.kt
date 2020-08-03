package com.yfbx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
inline fun <reified T> XAdapter.bind(layoutId: Int, item: T, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(layoutId, binder)
    add(item as Any)
}


inline fun <reified T> XAdapter.bind(layoutId: Int, items: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(layoutId, binder)
    @Suppress("UNCHECKED_CAST")
    addAll(items as List<Any>)
}


/**
 * 用 class name的 hashcode 作为 viewType
 */
inline fun <reified T> XAdapter.bind(layoutId: Int, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val type = T::class.java.name.hashCode()
    bind(type, layoutId, binder)
}


/**
 * 自己定义 viewType
 */
inline fun <reified T> XAdapter.bind(viewType: Int, layoutId: Int, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val className = T::class.java.name
    addBinder(viewType, className, object : Binder<T>(binder) {
        override fun createViewHelper(parent: ViewGroup): ViewHelper {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return object : ViewHelper(view) {
                override fun onBind(item: Any) {
                    binder.invoke(this, item as T)
                }
            }
        }
    })
}