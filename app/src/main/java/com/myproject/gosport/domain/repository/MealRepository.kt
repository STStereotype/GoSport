package com.myproject.gosport.domain.repository

import com.myproject.gosport.domain.models.Category
import com.myproject.gosport.domain.models.Meal
import com.myproject.gosport.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getCategories(): Flow<NetworkResult<List<Category>>>

    fun getMeals(): Flow<NetworkResult<List<Meal>>>
}
