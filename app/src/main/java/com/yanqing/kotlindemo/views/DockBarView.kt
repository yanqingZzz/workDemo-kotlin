package com.yanqing.kotlindemo.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.yanqing.kotlindemo.R
import kotlinx.android.synthetic.main.dock_bar_layout.view.*

class DockBarView : FrameLayout, View.OnClickListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        const val DOCK_BAR_HOME = 0
        const val DOCK_BAR_CATEGORY = 1
        const val DOCK_BAR_SEARCH = 2
        const val DOCK_BAR_PERSON = 3
    }

    private var mItemSelectedIndex: Int = -1
    private var mDockBarItemClickListener: OnDockBarItemClickListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.dock_bar_layout, this)
        dock_bar_item_home.setOnClickListener(this)
        dock_bar_item_category.setOnClickListener(this)
        dock_bar_item_search.setOnClickListener(this)
        dock_bar_item_person.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dock_bar_item_home -> setItemSelected(DOCK_BAR_HOME)
            R.id.dock_bar_item_category -> setItemSelected(DOCK_BAR_CATEGORY)
            R.id.dock_bar_item_search -> setItemSelected(DOCK_BAR_SEARCH)
            R.id.dock_bar_item_person -> setItemSelected(DOCK_BAR_PERSON)
        }
    }

    fun setItemSelected(index: Int) {
        if (index == mItemSelectedIndex) return
        resetItemSelected()
        when (index) {
            DOCK_BAR_HOME -> {
                dock_bar_home_icon.isSelected = true
                dock_bar_home_title.isSelected = true
            }
            DOCK_BAR_CATEGORY -> {
                dock_bar_category_icon.isSelected = true
                dock_bar_category_title.isSelected = true
            }
            DOCK_BAR_SEARCH -> {
                dock_bar_search_icon.isSelected = true
                dock_bar_search_title.isSelected = true
            }
            DOCK_BAR_PERSON -> {
                dock_bar_person_icon.isSelected = true
                dock_bar_person_title.isSelected = true
            }
        }
        mItemSelectedIndex = index;
        mDockBarItemClickListener?.onDockBarItemClick(index)
    }

    private fun resetItemSelected() {
        dock_bar_home_icon.isSelected = false
        dock_bar_home_title.isSelected = false
        dock_bar_category_icon.isSelected = false
        dock_bar_category_title.isSelected = false
        dock_bar_search_icon.isSelected = false
        dock_bar_search_title.isSelected = false
        dock_bar_person_icon.isSelected = false
        dock_bar_person_title.isSelected = false
    }

    fun setOnDockBarItemClickListener(listener: OnDockBarItemClickListener) {
        mDockBarItemClickListener = listener
    }

    interface OnDockBarItemClickListener {
        fun onDockBarItemClick(index: Int)
    }
}