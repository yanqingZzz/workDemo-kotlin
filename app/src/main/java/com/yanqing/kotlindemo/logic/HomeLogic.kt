package com.yanqing.kotlindemo.logic

import android.content.Context
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity
import com.yanqing.kotlindemo.db.helper.WorkDbHelper

class HomeLogic {
    companion object {
        const val HOME_LIST_ONE_PAGE_COUNT = 20

        fun getHomeListDataByPage(context: Context, page: Int, listener: OnGetHomeListListener?) {
            val dbHelper = WorkDbHelper.getInstance(context)
            val data = dbHelper.getAll()
            listener?.onSuccessByPage(data as ArrayList<WorkEntity>, false, page)
        }

        fun getHomeListData(context: Context, listener: OnGetHomeListListener?) {
            val categoryList = WorkDbHelper.getInstance(context).getCategoryByWeight()
            val data = WorkDbHelper.getInstance(context).getAll()
            listener?.onSuccess(data as ArrayList<WorkEntity>, categoryList as ArrayList<CategoryEntity>)
        }

        fun updateItemFavorite(context: Context, workEntity: WorkEntity) {
            val dbHelper = WorkDbHelper.getInstance(context)
            dbHelper.update(workEntity)
        }
    }


    interface OnGetHomeListListener {
        fun onSuccess(data: ArrayList<WorkEntity>, categoryList: ArrayList<CategoryEntity>)
        fun onSuccessByPage(data: ArrayList<WorkEntity>, hasMore: Boolean, page: Int)
        fun onFailed(page: Int)
    }
}