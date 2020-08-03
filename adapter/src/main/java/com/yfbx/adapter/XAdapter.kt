package com.yfbx.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2020-07-28
 * Description:
 */
class XAdapter : RecyclerView.Adapter<ViewHelper>() {

    //<viewType,binder>
    private val binders = SparseArrayCompat<Binder<*>>()

    //<className,viewType>
    private val types = hashMapOf<String, Int>()

    private val list = mutableListOf<Any>()

    fun addBinder(viewType: Int, className: String, binder: Binder<*>) {
        types[className] = viewType
        binders.append(viewType, binder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        val className = item::class.java.name
        val type = types[className]
        require(type != null) { "This type #$className of view  was not found!" }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHelper {
        val binder = binders[viewType]
        require(binder != null) { "This type #$viewType of view  was not found!" }
        return binder.createViewHelper(parent)
    }

    override fun onBindViewHolder(holder: ViewHelper, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: ViewHelper, position: Int, payloads: MutableList<Any>) {
        holder.onBind(list[holder.adapterPosition])
    }


    fun add(item: Any, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        require(item !is List<*>) {
            "IllegalArgumentException:Method #add(item) is only used to add single item.try: #addAll(items)"
        }
        list.add(position, item)
        notifyInserted(position)
    }

    fun addAll(items: List<Any>, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        list.addAll(position, items)
        notifyRangeInserted(items.size, position)
    }

    fun remove(position: Int) {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        list.removeAt(position)
        notifyRemoved(position)
    }

    fun update(position: Int, item: Any) {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        list[position] = item
        notifyItemChanged(position)
    }

    fun getList(): MutableList<Any> {
        return list
    }

    inline operator fun <reified T> get(position: Int): T? {
        require(position in 0 until itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        val item = getList()[position]
        return if (item is T) item else null
    }

    inline fun <reified T> getData(): List<T> {
        val list = mutableListOf<T>()
        getList().forEach {
            if (it is T) {
                list.add(it)
            }
        }
        return list
    }

    fun notifyRemoved(position: Int) {
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, list.size - position)
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
        val dataSize = list.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }
}

