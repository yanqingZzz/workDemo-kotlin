package com.yanqing.kotlindemo.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.yanqing.kotlindemo.R

class HomeItemHolder(itemView: View?) : AbsViewHolder(itemView) {
    public val mTitleView by lazy { itemView?.findViewById<TextView>(R.id.list_item_title) }
    public val mSubTitleView by lazy { itemView?.findViewById<TextView>(R.id.list_item_sub_title) }
    public val mTimeView by lazy { itemView?.findViewById<TextView>(R.id.list_item_time) }
    public val mFavoriteView by lazy { itemView?.findViewById<ImageView>(R.id.list_item_favorite) }
}