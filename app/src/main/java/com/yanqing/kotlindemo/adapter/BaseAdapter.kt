package com.yanqing.kotlindemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.adapter.holder.AbsViewHolder
import com.yanqing.kotlindemo.views.EmptyView
import com.yanqing.kotlindemo.views.ErrorView
import com.yanqing.kotlindemo.views.LoadingView

abstract class BaseAdapter : RecyclerView.Adapter<AbsViewHolder> {
    companion object BaseStatus{
        const val STATUS_LOADING = 100
        const val STATUS_ERROR = 101
        const val STATUS_LOADING_MORE = 102
        const val STATUS_ERROR_MORE = 103
        const val STATUS_EMPTY = 104
    }

    private var mContext: Context? = null
    private var mStatus: Int = STATUS_LOADING
    private var mIsMoreEnable: Boolean = false
    private var mHasMore: Boolean = false

    constructor(context: Context) {
        mContext = context
    }

    fun getContext(): Context? {
        return mContext
    }

    fun setStatus(status: Int) {
        mStatus = status
        notifyDataSetChanged()
    }

    fun getStatus(): Int {
        return mStatus
    }

    fun setMoreEnable(enable: Boolean) {
        mIsMoreEnable = enable
    }

    fun getMoreEnable(): Boolean {
        return mIsMoreEnable
    }

    fun setHasMore(hasMore: Boolean) {
        mHasMore = hasMore
    }

    fun getHasMore(): Boolean {
        return mHasMore
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbsViewHolder {
        when (viewType) {
            STATUS_LOADING -> return AbsViewHolder(LoadingView(mContext))
            STATUS_ERROR -> return AbsViewHolder(ErrorView(mContext))
            STATUS_LOADING_MORE -> return AbsViewHolder(LoadingView(mContext))
            STATUS_ERROR_MORE -> return AbsViewHolder(ErrorView(mContext))
            else -> return AbsViewHolder(EmptyView(mContext))
        }
    }

    override fun onBindViewHolder(holder: AbsViewHolder?, position: Int) {
        val type = getItemViewType(position)
        when (type) {
            STATUS_LOADING, STATUS_ERROR, STATUS_EMPTY -> {
                val params = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.MATCH_PARENT)
                val layout = holder?.itemView as LinearLayout
                layout.layoutParams = params
                layout.orientation = LinearLayout.VERTICAL
            }
            STATUS_LOADING_MORE, STATUS_ERROR_MORE -> {
                val height = mContext?.resources?.getDimension(R.dimen.base_adapter_loading_more_item_height)?.toInt()
                if (height != null) {
                    val params = AbsListView.LayoutParams(height,
                            AbsListView.LayoutParams.MATCH_PARENT)
                    val layout = holder?.itemView as LinearLayout
                    layout.layoutParams = params
                    layout.orientation = LinearLayout.HORIZONTAL

                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (mStatus == STATUS_LOADING || mStatus == STATUS_ERROR || mStatus == STATUS_EMPTY) {
            return 1;
        } else {
            if (mIsMoreEnable) {
                when (mHasMore) {
                    true -> return getDataListCount() + 1
                    false -> return getDataListCount()
                }
            } else {
                return getDataListCount()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mStatus == STATUS_LOADING || mStatus == STATUS_ERROR || mStatus == STATUS_EMPTY) {
            return mStatus;
        } else if (mIsMoreEnable && mHasMore && position == getDataListCount()) {
            when (mStatus) {
                STATUS_LOADING_MORE -> return STATUS_LOADING_MORE
                else -> return STATUS_ERROR_MORE
            }
        } else {
            return getNoneViewType(position)
        }
    }

    abstract fun getDataListCount(): Int
    abstract fun getNoneViewType(position: Int): Int
}