package com.yanqing.kotlindemo.db.utils

import android.content.Context
import com.yanqing.kotlindemo.db.constant.DbConstant
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.helper.WorkDbHelper

class DbUtils {
    companion object {
        fun initDataBase(context: Context) {
            val helper = WorkDbHelper.getInstance(context)
            if (helper.getCategoryCount() <= 0) {
                for (s in DbConstant.CATEGORY_DEFAULT) {
                    val category = CategoryEntity()
                    category.category = s
                    category.canEdit = false
                    category.timeStamp = System.currentTimeMillis()
                    category.weight = 1
                    helper.insertCategory(category)
                }
            }
        }
    }
}