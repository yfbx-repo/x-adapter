package com.yfbx.adapter

import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
abstract class Binder<T>(private val binder: (helper: ViewHelper, item: T) -> Unit) {

    abstract fun createViewHelper(parent: ViewGroup): ViewHelper

    @Suppress("UNCHECKED_CAST")
    fun onBind(helper: ViewHelper, item: Any) {
        binder.invoke(helper, item as T)
    }
}
