package com.yfbx.adapter

import android.view.View
import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
inline fun <reified T> XAdapter.bind(view: View, item: T, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(view, binder)
    add(item as Any)
}

inline fun <reified T> XAdapter.bind(view: View, items: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(view, binder)
    @Suppress("UNCHECKED_CAST")
    addAll(items as List<Any>)
}


inline fun <reified T> XAdapter.bind(view: View, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val type = T::class.java.name.hashCode()
    bind(type, view, binder)
}

inline fun <reified T> XAdapter.bind(viewType: Int, view: View, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val className = T::class.java.name
    addBinder(viewType, className, object : Binder<T>(binder) {
        override fun createViewHelper(parent: ViewGroup): ViewHelper {
            return object : ViewHelper(view) {
                override fun onBind(item: Any) {
                    binder.invoke(this, item as T)
                }
            }
        }
    })
}