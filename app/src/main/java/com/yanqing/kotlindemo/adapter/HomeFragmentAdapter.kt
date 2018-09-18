package com.yanqing.kotlindemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.adapter.holder.AbsViewHolder
import com.yanqing.kotlindemo.adapter.holder.HomeItemHolder
import com.yanqing.kotlindemo.db.entity.WorkEntity
import com.yanqing.kotlindemo.utils.Utils

class HomeFragmentAdapter : BaseAdapter {
    constructor(context: Context) : super(context) {
        setMoreEnable(true)
    }

    companion object {
        const val STATUS_NONE = 0;
    }

    private val mItems: ArrayList<WorkEntity> = ArrayList()

    private var mItemClickListener: View.OnClickListener? = null
    private var mItemFavoriteClickListener: View.OnClickListener? = null


    fun setItemData(items: ArrayList<WorkEntity>, hasMore: Boolean) {
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
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: AbsViewHolder?, position: Int) {
        val type = getItemViewType(position)
        when (type) {
            STATUS_NONE -> {
                val workEntity = mItems[position]
                val itemHolder = holder as HomeItemHolder
                itemHolder.mTitleView?.text = workEntity.title
                itemHolder.mSubTitleView?.text = workEntity.subTitle
                itemHolder.mTimeView?.text = Utils.formatTimeToString(workEntity.timeStamp!!)
                itemHolder.mFavoriteView?.isSelected = workEntity.favorite
                itemHolder.mFavoriteView?.tag = position
                itemHolder.itemView?.tag = position

                itemHolder.mFavoriteView?.setOnClickListener(mItemFavoriteClickListener)
                itemHolder.itemView?.setOnClickListener(mItemClickListener)
            }
            else -> {
                super.onBindViewHolder(holder, position)
            }
        }
    }

    override fun getNoneViewType(position: Int): Int {
        return STATUS_NONE
    }

    override fun getDataListCount(): Int {
        return mItems.size
    }
}