# x-adapter


[![](https://img.shields.io/badge/release-1.1.1-blue.svg)](https://github.com/yfbx-repo/x-adapter/releases)

### Dependency:
```
dependencies {
    implementation 'com.github.yfbx-repo:x-adapter:1.1.1'
}
```


### Example:

```
//单布局
val adapter = recycleView.bind(R.layout.item_menu, data) { helper, item ->
    helper.itemView.btn.text = item.name
}

//多布局
recycleView.bind {
     bind<CharSequence>(R.layout.item_menu_test, "Group-1") { helper, item ->
         val textView = helper.itemView.textView
         textView.setBackgroundResource(R.color.background)
         textView.setTextColor(Color.BLACK)
         textView.gravity = Gravity.START
         textView.text = item
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

     combine<CharSequence, String>()
     combine<CharSequence, SpannableString>()

     add(SpannableString("test"))
}
```
