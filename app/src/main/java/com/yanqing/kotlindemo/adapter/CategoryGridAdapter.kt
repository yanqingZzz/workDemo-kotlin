package com.yanqing.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.adapter.holder.AbsViewHolder
import com.yanqing.kotlindemo.adapter.holder.CategoryAddItemHolder
import com.yanqing.kotlindemo.adapter.holder.CategoryItemHolder
import com.yanqing.kotlindemo.db.entity.CategoryEntity

class CategoryGridAdapter : BaseAdapter {
    constructor(context: Context) : super(context)

    companion object {
        const val STATUS_NONE = 0;
        const val STATUS_ADD = 1;
    }

    private val mCategoryList = ArrayList<CategoryEntity>()
    private var mCategoryClickListener: View.OnClickListener? = null
    private var mCategoryAddClickListener: View.OnClickListener? = null


    fun setCategoryData(data: ArrayList<CategoryEntity>) {
        mCategoryList.clear()
        mCategoryList.addAll(data)
        if (mCategoryList.size > 0) {
            setStatus(STATUS_NONE)
        } else {
            setStatus(STATUS_EMPTY)
        }
    }

    fun reset() {
        mCategoryList.clear()
        setStatus(STATUS_LOADING)
    }

    fun setItemClickListener(listener: View.OnClickListener) {
        mCategoryClickListener = listener
    }

    fun setAddClickListener(listener: View.OnClickListener) {
        mCategoryAddClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbsViewHolder {
        when (viewType) {
            STATUS_NONE -> {
                val view = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item_layout, parent, false)
                return CategoryItemHolder(view)
            }
            STATUS_ADD -> {
                val view = LayoutInflater.from(getContext()).inflate(R.layout.category_list_add_item_layout, parent, false)
                return CategoryAddItemHolder(view)
            }
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: AbsViewHolder?, position: Int) {
        super.onBindViewHolder(holder, position)

        val viewType = getItemViewType(position)

        when (viewType) {
            STATUS_NONE -> {
                val categoryEntity = mCategoryList[position]
                val itemHolder = holder as CategoryItemHolder
                itemHolder.mCategory?.text = categoryEntity.category
                itemHolder.mCategory?.setOnClickListener(mCategoryClickListener)
            }
            STATUS_ADD -> {
                val itemHolder = holder as CategoryAddItemHolder
                itemHolder.mCategoryAddLayout?.setOnClickListener(mCategoryAddClickListener)
            }
        }
    }

    override fun getDataListCount(): Int {
        return mCategoryList.size + 1
    }

    override fun getNoneViewType(position: Int): Int {
        if (position == mCategoryList.size) {
            return STATUS_ADD
        }
        return STATUS_NONE
    }
}