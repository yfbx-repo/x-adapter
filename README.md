# x-adapter



### Example:

```
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
            //TODO: click event
        }
    }
    add("Group-2")
    bind<Int>(R.layout.item_menu_test, listOf(1, 2, 3)) { helper, item ->
        helper.itemView.textView.text = "test$item"
    }
    add("Group-3")
    addAl
    bind(TextView(this@MainActivity).apply {
        text = "TEST"
    })
}
```
