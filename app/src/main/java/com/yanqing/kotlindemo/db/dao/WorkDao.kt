package com.yanqing.kotlindemo.db.dao

import android.arch.persistence.room.*
import com.yanqing.kotlindemo.db.entity.WorkEntity

@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(work: WorkEntity)

    @Delete
    fun delete(work: WorkEntity)

    @Update
    fun update(work: WorkEntity)

    @Query("SELECT * FROM work_table WHERE id = :id")
    fun query(id: Int): WorkEntity

    @Query("SELECT * FROM work_table")
    fun queryAll(): List<WorkEntity>
}