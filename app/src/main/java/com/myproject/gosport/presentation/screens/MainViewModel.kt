package com.myproject.gosport.presentation.screens

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.myproject.gosport.domain.models.Category
import com.myproject.gosport.domain.models.Meal
import com.myproject.gosport.domain.usecases.GetCategoriesUseCase
import com.myproject.gosport.domain.usecases.GetMealsUseCase
import com.myproject.gosport.presentation.screens.models.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {
    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    private val _meals: MutableStateFlow<List<Meal>> = MutableStateFlow(listOf())
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Loading)
    val state: StateFlow<MainState> = _state

    fun changeCategory(categoryName: String) {
        val meals = _meals.value.filter { meal -> meal.categoryName == categoryName }
        _state.value = MainState.Success(
            meals = meals,
            categories = _categories.value
        )
    }

    fun loadData(lifecycleOwner: LifecycleOwner) {
        getMealsUseCase.invoke().asLiveData().observe(lifecycleOwner) {
            _meals.value = it.data!!
            updateStateSuccess()
        }
        getCategoriesUseCase.invoke().asLiveData().observe(lifecycleOwner) {
            _categories.value = it.data!!
            updateStateSuccess()
        }
        viewModelScope.launch {
            delay(1000)
            if (_meals.value.isEmpty() && _categories.value.isEmpty()) {
                _state.value =
                    MainState.Error("Время ожидания было превышено. Проверьте подключение к интернету")
            }
        }
    }

    private fun updateStateSuccess() {
        if (_meals.value.isNotEmpty() && _categories.value.isNotEmpty()) {
            val category = _categories.value[0]
            val meals = _meals.value.filter { item ->
                category.name == item.categoryName
            }
            _state.value = MainState.Success(meals, _categories.value)
        }
    }
}
