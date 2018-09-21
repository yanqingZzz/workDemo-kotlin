package com.yanqing.kotlindemo.recycler

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yanqing.kotlindemo.R

class CategoryItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val mPadding = context.resources.getDimension(R.dimen.category_item_padding).toInt()

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val position = parent?.getChildAdapterPosition(view)
        val location = position!! % 3
        when (location) {
            0 -> {
                outRect?.left = mPadding * 2 / 3
                outRect?.right = mPadding / 3
            }
            1 -> {
                outRect?.left = mPadding / 2
                outRect?.right = mPadding / 2
            }
            2 -> {
                outRect?.left = mPadding / 3
                outRect?.right = mPadding * 2 / 3
            }
        }

        outRect?.top = mPadding / 2
        outRect?.bottom = mPadding / 2
    }
}