package com.yanqing.kotlindemo.logic

import android.content.Context
import com.yanqing.kotlindemo.db.entity.WorkEntity
import com.yanqing.kotlindemo.db.helper.WorkDbHelper

class HomeLogic {
    companion object {
        const val HOME_LIST_ONE_PAGE_COUNT = 20

        fun getHomeListData(context: Context, page: Int, listener: OnGetHomeListListener?) {
            val dbHelper = WorkDbHelper.getInstance(context)
            val data = dbHelper.getAll()
            listener?.onSuccess(data as ArrayList<WorkEntity>, false, page)
        }

        fun updateItemFavorite(context: Context, workEntity: WorkEntity) {
            val dbHelper = WorkDbHelper.getInstance(context)
            dbHelper.update(workEntity)
        }
    }


    interface OnGetHomeListListener {
        fun onSuccess(data: ArrayList<WorkEntity>, hasMore: Boolean, page: Int)
        fun onFailed(page: Int)
    }
}