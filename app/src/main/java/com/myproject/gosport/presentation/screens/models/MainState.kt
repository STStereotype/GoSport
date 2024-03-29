package com.myproject.gosport.presentation.screens.models

import com.myproject.gosport.domain.models.Category
import com.myproject.gosport.domain.models.Meal

sealed class MainState {
    data object Loading : MainState()
    data class Success(
        val meals: List<Meal>,
        val categories: List<Category>
    ) : MainState()

    data class Error(val message: String) : MainState()
}
