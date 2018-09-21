package com.yanqing.kotlindemo.views

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.adapter.BaseAdapter
import com.yanqing.kotlindemo.adapter.CategoryGridAdapter
import com.yanqing.kotlindemo.adapter.holder.AbsViewHolder
import com.yanqing.kotlindemo.adapter.holder.CategoryItemHolder
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.recycler.CategoryItemDecoration

class HomeCategoryVew : FrameLayout, View.OnClickListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val MAX_SHOW_ITEM = 6
    }

    private val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.home_category_recycler) }
    private val mAdapter by lazy { HomeCategoryAdapter(context, HomeCategoryAdapter.STATUS_NONE) }
    private var mClickListener: OnCategoryClickListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.home_category_view_layout, this, true)

        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = CategorySpanSize(mAdapter)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(CategoryItemDecoration(context))

        mAdapter.setItemClickListener(this)
        mRecyclerView.adapter = mAdapter
    }

    fun setCategoryData(categoryList: ArrayList<CategoryEntity>) {
        mAdapter.setData(categoryList)
    }

    fun setOnCategoryClickListener(listener: OnCategoryClickListener?) {
        mClickListener = listener
    }

    override fun onClick(v: View?) {
        val position = v?.tag
        if (position != null && position is Int) {
            val category = mAdapter.getItem(position)
            mClickListener?.onClick(category)
        }
    }

    private class HomeCategoryAdapter(context: Context, status: Int) : BaseAdapter(context, status) {
        companion object {
            const val STATUS_NONE = 0
        }

        private val mCategoryList = ArrayList<CategoryEntity>()
        private var mItemClickListener: OnClickListener? = null

        fun setData(categoryList: ArrayList<CategoryEntity>) {
            mCategoryList.clear()
            mCategoryList.addAll(categoryList)
            setStatus(STATUS_NONE)
        }

        fun setItemClickListener(listener: OnClickListener) {
            mItemClickListener = listener
        }

        fun getItem(position: Int): CategoryEntity? {
            if (position > 0 && position < mCategoryList.size) {
                return mCategoryList[position]
            }
            return null
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbsViewHolder {
            val view = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item_layout, parent, false)
            return CategoryItemHolder(view)
        }

        override fun onBindViewHolder(holder: AbsViewHolder?, position: Int) {
            val categoryEntity = mCategoryList[position]
            val itemHolder = holder as CategoryItemHolder
            itemHolder.mCategory?.text = categoryEntity.category
            itemHolder.mCategory?.tag = position
            itemHolder.mCategoryCheck?.visibility = View.GONE
            itemHolder.mCategory?.isClickable = true
            itemHolder.mCategory?.setOnClickListener(mItemClickListener)
            itemHolder.itemView.setBackgroundColor(Color.WHITE)
        }

        override fun getDataListCount(): Int {
            return Math.min(MAX_SHOW_ITEM, mCategoryList.size)
        }

        override fun getNoneViewType(position: Int): Int {
            return STATUS_NONE
        }
    }

    private class CategorySpanSize(adapter: HomeCategoryAdapter?) : GridLayoutManager.SpanSizeLookup() {
        private val mAdapter = adapter
        override fun getSpanSize(position: Int): Int {
            val type = mAdapter?.getItemViewType(position)
            when (type) {
                CategoryGridAdapter.STATUS_NONE -> {
                    return 1
                }
                else -> return 3
            }
        }
    }

    interface OnCategoryClickListener {
        fun onClick(category: CategoryEntity?)
    }
}