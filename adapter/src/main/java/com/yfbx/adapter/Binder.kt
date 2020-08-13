package com.yfbx.adapter

import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
abstract class Binder<T>(private val binder: (helper: BaseViewHolder, item: T) -> Unit) {

    abstract fun createViewHelper(parent: ViewGroup): BaseViewHolder

    @Suppress("UNCHECKED_CAST")
    fun onBind(helper: BaseViewHolder, item: Any) {
        binder.invoke(helper, item as T)
    }
}
