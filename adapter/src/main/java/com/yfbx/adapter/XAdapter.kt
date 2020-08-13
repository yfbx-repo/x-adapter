package com.yfbx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.atomic.AtomicInteger

/**
 * Author: Edward
 * Date: 2020-07-28
 * Description:
 */
class XAdapter : BaseAdapter<Any, ViewHelper>() {

    //viewType
    private val nextType = AtomicInteger()

    //<viewType,binder>
    private val binders = SparseArrayCompat<Binder<*>>()

    //<className,viewType>
    private val types = hashMapOf<String, Int>()


    fun addType(className: String, binder: Binder<*>) {
        val viewType = nextType.getAndIncrement()
        types[className] = viewType
        binders.append(viewType, binder)
    }

    override fun getItemViewType(position: Int): Int {
        val item = get<Any>(position)!!
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

    override fun onBind(holder: ViewHelper, item: Any) {
        val type = getItemViewType(holder.adapterPosition)
        val binder = binders[type]
        binder?.onBind(holder, item)
    }
}


/**
 * 扩展 语法高亮
 */
fun RecyclerView.bind(builder: XAdapter.() -> Unit): XAdapter {
    val xAdapter = adapter(builder)
    adapter = xAdapter
    return xAdapter
}

fun adapter(builder: XAdapter.() -> Unit): XAdapter {
    return XAdapter().apply(builder)
}


inline fun <reified T> XAdapter.bind(layoutId: Int, item: T, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(layoutId, binder)
    add(item as Any)
}


inline fun <reified T> XAdapter.bind(layoutId: Int, items: List<T>, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    bind(layoutId, binder)
    @Suppress("UNCHECKED_CAST")
    addAll(items as List<Any>)
}

inline fun <reified T> XAdapter.bind(layoutId: Int, noinline binder: (helper: ViewHelper, item: T) -> Unit) {
    val className = T::class.java.name
    addType(className, object : Binder<T>(binder) {
        override fun createViewHelper(parent: ViewGroup): ViewHelper {
            return ViewHelper(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        }
    })
}

