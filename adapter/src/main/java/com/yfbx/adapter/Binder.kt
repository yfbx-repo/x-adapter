package com.yfbx.adapter

import android.view.ViewGroup

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
abstract class Binder<T>(val binder: (helper: ViewHelper, item: T) -> Unit) {

    abstract fun createViewHelper(parent: ViewGroup): ViewHelper
}
