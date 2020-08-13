package com.yfbx.adapter

import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
interface BaseTypeBinder {

    fun createViewHelper(parent: ViewGroup): BaseViewHolder

    fun onBind(helper: BaseViewHolder, item: Any)
}
