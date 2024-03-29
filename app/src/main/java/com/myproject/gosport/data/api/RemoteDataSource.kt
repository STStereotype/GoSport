package com.myproject.gosport.data.api

class RemoteDataSource(
    private val mealService: MealService
) {
    suspend fun getCategories() = mealService.getCategories()
    suspend fun getMeals() = mealService.getMeals()
}
