package com.myproject.gosport.di.modules

import android.content.Context
import androidx.room.Room
import com.myproject.gosport.data.api.MealService
import com.myproject.gosport.data.api.RemoteDataSource
import com.myproject.gosport.data.db.GoDatabase
import com.myproject.gosport.data.repository.MealRepositoryImpl
import com.myproject.gosport.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(mealService: MealService): RemoteDataSource =
        RemoteDataSource(mealService)

    @Provides
    @Singleton
    fun provideGpDatabase(@ApplicationContext context: Context): GoDatabase =
        Room.databaseBuilder(context, GoDatabase::class.java, "go_database").build()

    @Provides
    @Singleton
    fun provideMealRepositoryImpl(remote: RemoteDataSource, db: GoDatabase): MealRepository =
        MealRepositoryImpl(remote, db)
}
