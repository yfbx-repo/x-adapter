package com.yfbx.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2020-08-12
 * Description:
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    private val data = mutableListOf<T>()


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        onBind(holder, data[holder.adapterPosition])
    }

    abstract fun onBind(holder: BaseViewHolder, item: T)

    fun setNewData(items: List<T>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: T, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        require(item !is List<*>) {
            "IllegalArgumentException:Method #add(item) is only used to add single item.try: #addAll(items)"
        }
        data.add(position, item)
        notifyInserted(position)
    }

    fun addAll(items: List<T>, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.addAll(position, items)
        notifyRangeInserted(items.size, position)
    }

    fun remove(position: Int) {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.removeAt(position)
        notifyRemoved(position)
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
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data[position] = item
        notifyItemChanged(position)
    }

    fun getData(): MutableList<T> {
        return data
    }

    inline operator fun <reified T> get(position: Int): T? {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        val item = getData()[position]
        return if (item is T) item else null
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