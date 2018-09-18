package com.yanqing.kotlindemo.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

@Entity(tableName = "work_table")
class WorkEntity {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "title")
    var title: String? = null
    @ColumnInfo(name = "sub_title")
    var subTitle: String? = null
    @ColumnInfo(name = "category")
    var category: String? = null
    @ColumnInfo(name = "is_favorite")
    var favorite: Boolean = false
    @ColumnInfo(name = "content")
    var content: String? = null
    @ColumnInfo(name = "time_stamp")
    var timeStamp: Long? = null
}