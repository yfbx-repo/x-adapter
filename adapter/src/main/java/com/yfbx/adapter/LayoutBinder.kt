package com.yfbx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-13
 * Description:
 */
class LayoutBinder<T>(private val layoutId: Int, private val binder: (helper: BaseViewHolder, item: T) -> Unit) : BaseTypeBinder {

    override fun createViewHelper(parent: ViewGroup): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBind(helper: BaseViewHolder, item: Any) {
        @Suppress("UNCHECKED_CAST")
        binder.invoke(helper, item as T)
    }
}