package com.myproject.gosport.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myproject.gosport.data.entity.CategoryResponse
import com.myproject.gosport.data.entity.CategoryResponse.Category
import com.myproject.gosport.data.entity.MealResponse
import com.myproject.gosport.data.entity.MealResponse.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoDao {
    // Category
    @Query("SELECT * FROM ${CategoryResponse.CATEGORY_TABLE_NAME}")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: List<Category>)

    @Query("DELETE FROM ${CategoryResponse.CATEGORY_TABLE_NAME}")
    suspend fun deleteAllCategory()

    // Meal
    @Query("SELECT * FROM ${MealResponse.MEAL_TABLE_NAME}")
    fun getMeals(): Flow<List<Meal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(category: List<Meal>)

    @Query("DELETE FROM ${MealResponse.MEAL_TABLE_NAME}")
    suspend fun deleteAllMeal()
}
