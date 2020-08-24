package com.yfbx.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2020-08-12
 * Description:
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {
    val TAG = "XAdapter"
    private val data = mutableListOf<T>()


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBind(holder, data[holder.adapterPosition])
    }

    abstract fun onBind(holder: BaseViewHolder, item: T)

    fun setNewData(items: List<T>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: T, position: Int = itemCount) {
        require(item !is List<*>) {
            "IllegalArgumentException:Method #add(item) is only used to add single item.try: #addAll(items)"
        }
        if (position in 0..itemCount) {
            data.add(position, item)
            notifyInserted(position)
        } else {
            Log.e(TAG, "IndexOutOfBoundsException: size = $itemCount, position = $position")
        }
    }

    fun addAll(items: List<T>, position: Int = itemCount) {
        if (position in 0..itemCount) {
            data.addAll(position, items)
            notifyRangeInserted(items.size, position)
        } else {
            Log.e(TAG, "IndexOutOfBoundsException: size = $itemCount, position = $position")
        }
    }

    fun remove(position: Int) {
        if (position in 0..itemCount) {
            data.removeAt(position)
            notifyRemoved(position)
        } else {
            Log.e(TAG, "IndexOutOfBoundsException: size = $itemCount, position = $position")
        }
    }

    inline fun <reified T> removeAll() {
        getData().removeAll { it is T }
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun update(position: Int, item: T) {
        if (position in 0..itemCount) {
            data[position] = item
            notifyItemChanged(position)
        } else {
            Log.e(TAG, "IndexOutOfBoundsException: size = $itemCount, position = $position")
        }
    }

    fun getData(): MutableList<T> {
        return data
    }

    inline operator fun <reified T> get(position: Int): T? {
        if (position in 0..itemCount) {
            val item = getData()[position]
            return if (item is T) item else null
        } else {
            Log.e(TAG, "IndexOutOfBoundsException: size = $itemCount, position = $position")
            return null
        }

    }

    inline fun <reified T> getAll(): List<T> {
        val list = mutableListOf<T>()
        getData().forEach {
            if (it is T) {
                list.add(it)
            }
        }
        return list
    }

    fun notifyRemoved(position: Int) {
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, data.size - position)
    }

    fun notifyRangeInserted(size: Int, position: Int) {
        notifyItemRangeInserted(position, size)
        compatibilityDataSizeChanged(size)
    }

    fun notifyInserted(position: Int) {
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }


    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = data.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }
}