package com.yfbx.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Author: Edward
 * Date: 2020-08-03
 * Description:
 */
open class BaseViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer