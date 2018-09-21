package com.yanqing.kotlindemo.adapter.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.yanqing.kotlindemo.R

class CategoryAddItemHolder(itemView: View) : AbsViewHolder(itemView) {
    val mCategoryAddLayout = itemView.findViewById<LinearLayout>(R.id.category_add_layout)
}