package com.myproject.gosport.di.modules

import com.myproject.gosport.domain.repository.MealRepository
import com.myproject.gosport.domain.usecases.GetCategoriesUseCase
import com.myproject.gosport.domain.usecases.GetMealsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(mealRepository: MealRepository): GetCategoriesUseCase =
        GetCategoriesUseCase(mealRepository)

    @Provides
    @Singleton
    fun provideGetMealsUseCase(mealRepository: MealRepository): GetMealsUseCase =
        GetMealsUseCase(mealRepository)
}
