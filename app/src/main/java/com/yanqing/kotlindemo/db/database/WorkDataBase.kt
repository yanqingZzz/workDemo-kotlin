package com.yanqing.kotlindemo.db.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.yanqing.kotlindemo.db.dao.CategoryDao
import com.yanqing.kotlindemo.db.dao.WorkDao
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity

@Database(entities = arrayOf(WorkEntity::class, CategoryEntity::class), version = 1, exportSchema = false)
abstract class WorkDataBase : RoomDatabase() {
    abstract fun workDao(): WorkDao
    abstract fun categoryDao(): CategoryDao
}