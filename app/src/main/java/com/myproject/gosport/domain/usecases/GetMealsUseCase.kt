package com.myproject.gosport.domain.usecases

import com.myproject.gosport.domain.models.Meal
import com.myproject.gosport.domain.repository.MealRepository
import com.myproject.gosport.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetMealsUseCase(
    private val mealRepository: MealRepository
) {
    fun invoke(): Flow<NetworkResult<List<Meal>>> = mealRepository.getMeals()
}
