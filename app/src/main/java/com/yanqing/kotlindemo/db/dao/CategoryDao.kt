package com.yanqing.kotlindemo.db.dao

import android.arch.persistence.room.*
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(category: CategoryEntity)

    @Delete
    fun delete(category: CategoryEntity)

    @Update
    fun update(category: CategoryEntity)

    @Query("SELECT * FROM category_table WHERE id = :id")
    fun query(id: Int): CategoryEntity

    @Query("SELECT * FROM category_table")
    fun queryAll(): List<CategoryEntity>

    @Query("UPDATE category_table SET weight = weight + 1 WHERE id = :id")
    fun categoryUse(id: Int)

    @Query("SELECT * FROM category_table ORDER BY weight DESC LIMIT 6")
    fun getCategoryByWeight(): List<CategoryEntity>

    @Query("SELECT count(*) FROM category_table")
    fun getCategoryCount(): Int
}