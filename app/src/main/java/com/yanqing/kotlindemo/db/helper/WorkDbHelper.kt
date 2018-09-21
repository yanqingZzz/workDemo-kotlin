package com.yanqing.kotlindemo.db.helper

import android.arch.persistence.room.Room
import android.content.Context
import com.yanqing.kotlindemo.db.constant.DbConstant
import com.yanqing.kotlindemo.db.database.WorkDataBase
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity

class WorkDbHelper constructor(context: Context) {
    private val mDbInstance = Room.databaseBuilder(context, WorkDataBase::class.java, "work_db")
            .allowMainThreadQueries().build()

    companion object {
        var mHelperInstance: WorkDbHelper? = null

        fun getInstance(context: Context): WorkDbHelper {
            if (mHelperInstance == null) {
                synchronized(WorkDbHelper) {
                    if (mHelperInstance == null) {
                        mHelperInstance = WorkDbHelper(context)
                    }
                }
            }
            return mHelperInstance!!
        }
    }

    fun insert(work: WorkEntity) {
        mDbInstance.workDao().Insert(work)
    }

    fun getAll(): List<WorkEntity> {
        return mDbInstance.workDao().queryAll()
    }

    fun getWorkById(id: Int): WorkEntity {
        return mDbInstance.workDao().query(id)
    }

    fun delete(work: WorkEntity) {
        mDbInstance.workDao().delete(work)
    }

    fun update(work: WorkEntity) {
        mDbInstance.workDao().update(work)
    }

    fun insertCategory(categoryEntity: CategoryEntity) {
        mDbInstance.categoryDao().Insert(categoryEntity)
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        mDbInstance.categoryDao().delete(categoryEntity)
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        mDbInstance.categoryDao().update(categoryEntity)
    }

    fun getAllCategory(): List<CategoryEntity> {
        return mDbInstance.categoryDao().queryAll()
    }

    fun getCategoryById(id: Int): CategoryEntity {
        return mDbInstance.categoryDao().query(id)
    }

    fun categoryClick(id: Int) {
        mDbInstance.categoryDao().categoryUse(id)
    }

    fun getCategoryByWeight(): List<CategoryEntity> {
        return mDbInstance.categoryDao().getCategoryByWeight()
    }

    fun getCategoryCount(): Int {
        return mDbInstance.categoryDao().getCategoryCount()
    }
}