package com.yfbx.demo

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.yfbx.adapter.bind
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.item_menu_test.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = listOf(User("Edward"), User("David"), User("Jackson"), User("Roger"), User("Sara"))

        //单布局
        val adapter = recycleView.bind(R.layout.item_menu, data) { helper, item ->
            helper.itemView.btn.text = item.name
        }

        //多布局
        recycleView.bind {
            bind(R.layout.item_menu_test, "Group-1") { helper, item ->
                helper.itemView.textView.setBackgroundResource(R.color.background)
                helper.itemView.textView.setTextColor(Color.BLACK)
                helper.itemView.textView.gravity = Gravity.START
                helper.itemView.textView.text = item
            }
            bind<User>(R.layout.item_menu, data) { helper, item ->
                helper.itemView.btn.text = item.name
                helper.itemView.btn.setOnClickListener {
                    //
                }
            }
            add("Group-2")
            bind<Int>(R.layout.item_menu_test, listOf(1, 2, 3)) { helper, item ->
                helper.itemView.textView.text = "test$item"
            }
            add("Group-3")
            addAll(data)
        }
    }
}