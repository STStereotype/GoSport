package com.myproject.gosport.data.api

import com.myproject.gosport.data.entity.CategoryResponse
import com.myproject.gosport.data.entity.MealResponse
import retrofit2.Response
import retrofit2.http.GET

interface MealService {
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("search.php?s")
    suspend fun getMeals(): Response<MealResponse>
}