package com.yanqing.kotlindemo.adapter

import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.util.SparseBooleanArray
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
    private val mCheckStatus = SparseBooleanArray()

    private var mCategoryClickListener: View.OnClickListener? = null
    private var mCategoryLongClickListener: View.OnLongClickListener? = null
    private var mCategoryAddClickListener: View.OnClickListener? = null

    private var mCheckPattern = false


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

    fun setItemLongClickListener(listener: View.OnLongClickListener) {
        mCategoryLongClickListener = listener
    }

    fun setAddClickListener(listener: View.OnClickListener) {
        mCategoryAddClickListener = listener
    }

    fun deleteItem(position: Int): CategoryEntity {
        return mCategoryList.removeAt(position)
    }

    fun getItem(position: Int): CategoryEntity {
        return mCategoryList[position]
    }

    fun selectAll() {
        val select = checkIsAllSelect()
        for (entity in mCategoryList) {
            if (entity.canEdit) {
                mCheckStatus.put(entity.id, !select)
            }
        }
        notifyDataSetChanged()
    }

    fun startCheckPattern() {
        mCheckPattern = true
        notifyDataSetChanged()
    }

    fun stopCheckPattern() {
        mCheckPattern = false
        mCheckStatus.clear()
        notifyDataSetChanged()
    }

    fun isCheckPattern(): Boolean {
        return mCheckPattern
    }

    fun getCheckItem(): ArrayList<CategoryEntity> {
        val categoryList = ArrayList<CategoryEntity>()
        for (entity in mCategoryList) {
            if (mCheckStatus[entity.id, false]) {
                categoryList.add(entity)
            }
        }
        return categoryList
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
                itemHolder.mCategory?.tag = position

                when (mCheckPattern) {
                    true -> {
                        if (categoryEntity.canEdit) {
                            itemHolder.mCategoryCheck?.visibility = View.VISIBLE
                            itemHolder.mCategoryCheck?.isChecked = mCheckStatus[categoryEntity.id, false]
                            itemHolder.mCategory?.isClickable = true
                            itemHolder.mCategory?.setOnClickListener(mItemCheckClickListener)
                            itemHolder.mCategory?.setOnLongClickListener(null)
                            itemHolder.itemView.setBackgroundColor(Color.WHITE)
                        } else {
                            itemHolder.mCategoryCheck?.visibility = View.GONE
                            itemHolder.mCategory?.isClickable = false
                            itemHolder.itemView.setBackgroundColor(Color.GRAY)
                        }
                    }
                    false -> {
                        itemHolder.mCategoryCheck?.visibility = View.GONE
                        itemHolder.mCategory?.isClickable = true
                        itemHolder.mCategory?.setOnClickListener(mCategoryClickListener)
                        itemHolder.mCategory?.setOnLongClickListener(mCategoryLongClickListener)
                        itemHolder.itemView.setBackgroundColor(Color.WHITE)
                    }
                }

            }
            STATUS_ADD -> {
                val itemHolder = holder as CategoryAddItemHolder
                itemHolder.mCategoryAddLayout?.setOnClickListener(mCategoryAddClickListener)
            }
        }
    }

    override fun getDataListCount(): Int {
        when (mCheckPattern) {
            true -> return mCategoryList.size
            false -> return mCategoryList.size + 1
        }
    }

    override fun getNoneViewType(position: Int): Int {
        if (!mCheckPattern && position == mCategoryList.size) {
            return STATUS_ADD
        }
        return STATUS_NONE
    }

    private fun checkIsAllSelect(): Boolean {
        for (entity in mCategoryList) {
            if (entity.canEdit && !mCheckStatus[entity.id, false]) {
                return false
            }
        }
        return true
    }

    private val mItemCheckClickListener = View.OnClickListener {
        val position = it.tag as Int
        val categoryEntity = mCategoryList[position]
        mCheckStatus.put(categoryEntity.id, !mCheckStatus[categoryEntity.id, false])
        notifyItemChanged(position)
    }
}