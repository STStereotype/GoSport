package com.myproject.gosport.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myproject.gosport.data.entity.CategoryResponse
import com.myproject.gosport.data.entity.MealResponse

@Database(
    entities = [
        CategoryResponse.Category::class,
        MealResponse.Meal::class
    ], version = 1, exportSchema = true
)
abstract class GoDatabase : RoomDatabase() {
    abstract fun goDao(): GoDao
}
