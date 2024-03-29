package com.myproject.gosport.domain.usecases

import com.myproject.gosport.domain.models.Category
import com.myproject.gosport.domain.repository.MealRepository
import com.myproject.gosport.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val mealRepository: MealRepository
) {
    fun invoke(): Flow<NetworkResult<List<Category>>> = mealRepository.getCategories()
}
