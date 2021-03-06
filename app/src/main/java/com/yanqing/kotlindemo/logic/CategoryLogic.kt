package com.yanqing.kotlindemo.logic

import android.content.Context
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.helper.WorkDbHelper

class CategoryLogic {

    companion object {
        fun getCategoryData(context: Context, listener: OnGetCategoryListListener?) {
            val categoryList = WorkDbHelper.getInstance(context).getAllCategory()
            listener?.onSuccess(categoryList as ArrayList<CategoryEntity>)
        }

        fun getCategoryDataByWeight(context: Context, listener: OnGetCategoryListListener?) {
            val categoryList = WorkDbHelper.getInstance(context).getCategoryByWeight()
            listener?.onSuccess(categoryList as ArrayList<CategoryEntity>)
        }

        fun findCategoryById(context: Context, id: Int, listener: OnGetCategoryListener?) {
            val category = WorkDbHelper.getInstance(context).getCategoryById(id)
            listener?.onSuccess(id, category)
        }

        fun insert(context: Context, categoryEntity: CategoryEntity) {
            WorkDbHelper.getInstance(context).insertCategory(categoryEntity)
        }

        fun update(context: Context, categoryEntity: CategoryEntity) {
            WorkDbHelper.getInstance(context).updateCategory(categoryEntity)
        }

        fun delete(context: Context, categoryEntity: CategoryEntity) {
            WorkDbHelper.getInstance(context).deleteCategory(categoryEntity)
        }

        fun delete(context: Context, categoryList: ArrayList<CategoryEntity>?) {
            for (categoryEntity in categoryList!!) {
                delete(context, categoryEntity)
            }
        }

        fun categoryClick(context: Context, id: Int) {
            WorkDbHelper.getInstance(context).categoryClick(id)
        }
    }

    interface OnGetCategoryListener {
        fun onSuccess(id: Int, categoryEntity: CategoryEntity)
        fun onFailed(id: Int)
    }

    interface OnGetCategoryListListener {
        fun onSuccess(data: ArrayList<CategoryEntity>)
        fun onFailed(page: Int)
    }
}