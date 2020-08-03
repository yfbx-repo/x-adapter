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

    val data = mutableListOf<Any>()

    fun addBinder(viewType: Int, className: String, binder: Binder<*>) {
        types[className] = viewType
        binders.append(viewType, binder)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
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
        holder.onBind(data[holder.adapterPosition])
    }


    fun add(item: Any, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        require(item !is List<*>) {
            "IllegalArgumentException:Method #add(item) is only used to add single item.try: #addAll(items)"
        }
        data.add(position, item)
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }

    fun addAll(items: List<Any>, position: Int = itemCount) {
        require(position in 0..itemCount) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
        compatibilityDataSizeChanged(items.size)
    }

    fun remove(position: Int) {
        require(position in 0 until data.size) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data.removeAt(position)
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, data.size - position)
    }

    fun update(position: Int, item: Any) {
        require(position in 0 until data.size) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        data[position] = item
        notifyItemChanged(position)
    }

    inline operator fun <reified T> get(position: Int): T? {
        require(position in 0 until data.size) {
            "IndexOutOfBoundsException: size = $itemCount, position = $position"
        }
        val item = data[position]
        return if (item is T) item else null
    }


    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = data.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }
}

