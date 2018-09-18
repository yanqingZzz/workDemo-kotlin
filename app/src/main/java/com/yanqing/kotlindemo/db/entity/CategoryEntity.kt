package com.yanqing.kotlindemo.db.entity

import android.arch.persistence.room.*

@Entity(tableName = "category_table")
class CategoryEntity {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "category")
    var category: String? = null
    @ColumnInfo(name = "time_stamp")
    var timeStamp: Long? = null

    @Ignore
    var canEdit: Boolean = true
}