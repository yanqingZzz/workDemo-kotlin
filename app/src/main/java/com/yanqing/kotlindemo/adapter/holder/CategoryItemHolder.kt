package com.yanqing.kotlindemo.adapter.holder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.yanqing.kotlindemo.R

class CategoryItemHolder(itemView: View) : AbsViewHolder(itemView) {
    val mCategory = itemView.findViewById<TextView>(R.id.category_title)
    val mCategoryCheck = itemView.findViewById<CheckBox>(R.id.category_check)
}