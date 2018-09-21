package com.yanqing.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.adapter.holder.AbsViewHolder
import com.yanqing.kotlindemo.adapter.holder.HomeCategoryItemHolder
import com.yanqing.kotlindemo.adapter.holder.HomeItemHolder
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity
import com.yanqing.kotlindemo.utils.Utils
import com.yanqing.kotlindemo.views.HomeCategoryVew

class HomeFragmentAdapter : BaseAdapter {
    constructor(context: Context) : super(context) {
        setMoreEnable(true)
    }

    companion object {
        const val STATUS_NONE = 0
        const val STATUS_CATEGORY = 1
    }

    private val mItems: ArrayList<WorkEntity> = ArrayList()
    private val mCategoryItems: ArrayList<CategoryEntity> = ArrayList()

    private var mItemClickListener: View.OnClickListener? = null
    private var mItemFavoriteClickListener: View.OnClickListener? = null
    private var mCategoryItemClickListener: HomeCategoryVew.OnCategoryClickListener? = null

    fun setItemData(items: ArrayList<WorkEntity>, categoryItem: ArrayList<CategoryEntity>) {
        mItems.clear()
        mCategoryItems.clear()

        mItems.addAll(items)
        mCategoryItems.addAll(categoryItem)

//        setHasMore(true)
        setHasMore(false)
        if (mItems.size > 0) {
            setStatus(STATUS_NONE)
        } else {
            setStatus(STATUS_EMPTY)
        }
    }

    fun setItemDataByPage(items: ArrayList<WorkEntity>, hasMore: Boolean) {
        mItems.addAll(items)
        setHasMore(hasMore)
        if (mItems.size > 0) {
            setStatus(STATUS_NONE)
        } else {
            setStatus(STATUS_EMPTY)
        }
    }

    fun reset() {
        mItems.clear()
        setStatus(STATUS_LOADING)
    }

    fun setItemClickListener(listener: View.OnClickListener?) {
        mItemClickListener = listener
    }

    fun setItemFavoriteClickListener(listener: View.OnClickListener?) {
        mItemFavoriteClickListener = listener
    }

    fun setCategoryItemClickListener(listener: HomeCategoryVew.OnCategoryClickListener?) {
        mCategoryItemClickListener = listener
    }

    fun setItemFavorite(position: Int, favorite: Boolean) {
        mItems[position].favorite = favorite
    }

    fun getItemData(position: Int): WorkEntity {
        return mItems[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbsViewHolder {
        when (viewType) {
            STATUS_NONE -> {
                val view = LayoutInflater.from(getContext()).inflate(R.layout.home_list_item_layout, parent, false)
                return HomeItemHolder(view)
            }
            STATUS_CATEGORY -> {
                return HomeCategoryItemHolder(HomeCategoryVew(getContext()))
            }
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: AbsViewHolder?, position: Int) {
        val type = getItemViewType(position)
        when (type) {
            STATUS_NONE -> {
                if (holder != null && holder is HomeItemHolder) {
                    var realPosition = position
                    if (mCategoryItems.size > 0) {
                        realPosition--;
                    }
                    val workEntity = mItems[realPosition]

                    holder.mTitleView?.text = workEntity.title
                    holder.mSubTitleView?.text = workEntity.subTitle
                    holder.mDetailView?.text = workEntity.content
                    holder.mCategoryView?.text = workEntity.category
                    holder.mTimeView?.text = Utils.formatTimeToString(workEntity.timeStamp!!)
                    holder.mFavoriteView?.isSelected = workEntity.favorite
                    holder.mFavoriteView?.tag = position
                    holder.itemView?.tag = position

                    holder.mFavoriteView?.setOnClickListener(mItemFavoriteClickListener)
                    holder.itemView?.setOnClickListener(mItemClickListener)
                }
            }
            STATUS_CATEGORY -> {
                if (holder != null && holder is HomeCategoryItemHolder) {
                    holder.mRecyclerView.setCategoryData(mCategoryItems)
                    holder.mRecyclerView.setOnCategoryClickListener(mCategoryItemClickListener)
                }
            }
            else -> {
                super.onBindViewHolder(holder, position)
            }
        }
    }

    override fun getNoneViewType(position: Int): Int {
        if (position == 0 && mCategoryItems.size > 0) {
            return STATUS_CATEGORY
        }
        return STATUS_NONE
    }

    override fun getDataListCount(): Int {
        var count = 0
        if (mCategoryItems.size > 0) {
            count = mItems.size + 1
        } else {
            count = mItems.size
        }
        return count
    }
}